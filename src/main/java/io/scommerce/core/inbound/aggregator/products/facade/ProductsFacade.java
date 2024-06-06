package io.scommerce.core.inbound.aggregator.products.facade;

import com.google.cloud.bigquery.FieldValue;
import com.google.cloud.bigquery.TableResult;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import io.scommerce.core.inbound.aggregator.clients.biqquery.BigQueryClient;
import io.scommerce.core.inbound.aggregator.products.data.PageableData;
import io.scommerce.core.inbound.aggregator.products.data.Product;
import io.scommerce.core.inbound.aggregator.products.data.ProductPageableData;
import io.scommerce.core.inbound.aggregator.products.data.StockPageableData;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

@Service
@AllArgsConstructor
@Slf4j
public class ProductsFacade {

    private BigQueryClient bigQueryClient;

    private static final BiFunction<String, String, Optional<String>> getValueFromJson =
            (fieldValue, name) -> {
                Configuration pathConfiguration =
                        Configuration.builder().options(Option.SUPPRESS_EXCEPTIONS, Option.DEFAULT_PATH_LEAF_TO_NULL).build();
                return Optional.ofNullable(JsonPath.using(pathConfiguration).parse(fieldValue).read(name, String.class));
            };

    private static final BiFunction<FieldValue, String, String> getValueString =
            (fieldValue, name) -> {
                try {
                    return fieldValue.getStringValue();
                } catch (Exception e) {
                    log.error("issue with bigquery data" + name, e);
                    return "";
                }
            };

    @SneakyThrows
    public Mono<ProductPageableData> getBaseProducts(String token, int pageSize, String search) {
        String query = "select id, name[0].value, TO_JSON_STRING(t.variants), TO_JSON_STRING(t.stores.e5p.categories) AS categories FROM `marketcom-bi.marketplace.products` as t" +
                " where t.createtime = (select max(t1.createtime) " +
                " from `marketcom-bi.marketplace.products` as t1 where t1.id = t.id) ";

        if (StringUtils.isNoneBlank(search)) {
            boolean number = NumberUtils.isParsable(search);

            var queryForData = number ? String.format("AND t.id = %s", search)
                    : String.format("AND (EXISTS (SELECT * FROM UNNEST(stores.e5p.categories) AS x WHERE x.slug  =  '%s' LIMIT 1 ))", search);

            query = String.format(query + " %s", queryForData);
        }

        String finalQuery = query;
        return Mono.fromCallable(() -> {
                    TableResult rest = bigQueryClient.queryPagination(finalQuery, token, pageSize);

                    var responseToken = rest.getNextPageToken();
                    var totalRows = rest.getTotalRows();
                    List<Product> products = new ArrayList<>();
                    rest.getValues().forEach(i -> {
                        var base = getValueString.apply(i.get(0), "id");
                        var name = getValueString.apply(i.get(1), "name[0].value");
                        var variant = getValueString.apply(i.get(2), "variant");
                        var categories = getValueString.apply(i.get(3), "categories");

                        var colorId = getValueFromJson.apply(variant, "attributes.color.id");
                        var sizeId = getValueFromJson.apply(variant, "attributes.size.id");
                        var ean = getValueFromJson.apply(variant, "ean");

                        Product product = new Product();
                        ean.ifPresent(product::setEan);
                        product.setBaseId(base);
                        product.setName(name);
                        product.setVariants(variant);
                        product.setCategories(categories);

                        if (base != null && colorId.isPresent() && sizeId.isPresent()) {
                            product.setCode(String.format("%s-%s-%s", base, colorId.get(), sizeId.get()));
                        } else {
                            if (base != null && colorId.isPresent()) {
                                product.setCode(String.format("%s-%s", base, colorId.get()));
                            }
                        }
                        products.add(product);
                    });

                    return new ProductPageableData(products, new PageableData(responseToken, totalRows, pageSize));
                })
                .subscribeOn(Schedulers.boundedElastic());
    }

    @SneakyThrows
    public Mono<ProductPageableData> getProducts(String token, int pageSize, String search) {
        var query =
                "SELECT t.id , t.name[0].value, TO_JSON_STRING(variant) AS variant, TO_JSON_STRING(t.stores.e5p.categories) AS categories " +
                        "FROM `marketcom-bi.marketplace.products` as t  " +
                        "CROSS JOIN UNNEST(t.variants) AS variant " +
                        "WHERE t.createtime = (select max(t1.createtime) FROM `marketcom-bi.marketplace.products` as t1 where t1.id = t.id) ";

        if (StringUtils.isNotEmpty(search)) {


            boolean number = NumberUtils.isParsable(search);

            var queryForData = number ? String.format("AND (t.id = %s OR variant.ean = %s)", search, search)
                    : String.format("AND (EXISTS (SELECT * FROM UNNEST(stores.e5p.categories) AS x WHERE x.slug = '%s' LIMIT 1 ))", search);

            query =
                    String.format(query + "%s", queryForData);
        }

        String finalQuery = query;
        return Mono.fromCallable(() -> {
                    TableResult rest = bigQueryClient.queryPagination(finalQuery, token, pageSize);

                    var responseToken = rest.getNextPageToken();
                    var totalRows = rest.getTotalRows();
                    List<Product> products = new ArrayList<>();
                    rest.getValues().forEach(i -> {
                        var base = getValueString.apply(i.get(0), "id");
                        var name = getValueString.apply(i.get(1), "name[0].value");
                        var variant = getValueString.apply(i.get(2), "variant");
                        var categories = getValueString.apply(i.get(3), "categories");

                        var colorId = getValueFromJson.apply(variant, "attributes.color.id");
                        var sizeId = getValueFromJson.apply(variant, "attributes.size.id");
                        var ean = getValueFromJson.apply(variant, "ean");

                        Product product = new Product();
                        ean.ifPresent(product::setEan);
                        product.setBaseId(base);
                        product.setName(name);
                        product.setVariants(variant);
                        product.setCategories(categories);

                        if (base != null && colorId.isPresent() && sizeId.isPresent()) {
                            product.setCode(String.format("%s-%s-%s", base, colorId.get(), sizeId.get()));
                        } else {
                            if (base != null && colorId.isPresent()) {
                                product.setCode(String.format("%s-%s", base, colorId.get()));
                            }
                        }
                        products.add(product);
                    });

                    return new ProductPageableData(products, new PageableData(responseToken, totalRows, pageSize));
                })
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<StockPageableData> getStock(String token, int pageSize, String search) {
        var query =
                "SELECT t.ean,t.available FROM `marketcom-bi.marketplace.stock` as t WHERE t.createtime = (select max(t1.createtime)" +
                        " FROM `marketcom-bi.marketplace.stock` as t1 where t1.ean = t.ean)";

        if (StringUtils.isNotEmpty(search) && NumberUtils.isParsable(search)) {
            query =
                    String.format(query + " AND  t.ean = %s", search);
        }

        String finalQuery = query;
        return Mono.fromCallable(() -> {
                    TableResult rest = bigQueryClient.queryPagination(finalQuery, token, pageSize);

                    var responseToken = rest.getNextPageToken();
                    var totalRows = rest.getTotalRows();
                    List<Product> products = new ArrayList<>();
                    rest.getValues().forEach(i -> {
                        var ean = getValueString.apply(i.get(0), "ean");
                        var available = getValueString.apply(i.get(1), "available");

                        Product product = new Product();
                        product.setEan(ean);
                        product.setStock(Integer.parseInt(available));
                        products.add(product);
                    });

                    return new StockPageableData(products, new PageableData(responseToken, totalRows, pageSize));
                })
                .subscribeOn(Schedulers.boundedElastic());
    }
}
