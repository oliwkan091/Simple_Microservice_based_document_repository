package com.Microservice_based_document_repository.Files_transfer.repository;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.spi.MetadataBuilderContributor;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.BooleanType;

/**
 * JpaRepository do not allow to build advanced custom query's like postgreSQL internal query's so
 * MetadataBuilderContributor is used for this one purpose instead
 */
public class SqlFunctionsMetadataBuilderContributor implements MetadataBuilderContributor {

    /**
     * Function with name fullsearch is being build which uses postgreSQL advanced queries for full search.
     * to_tsvector - tokenizes words provided by database field search_name to make it easier to search in.
     * plainto_tsquery searches previous filed with provided by user keys.
     * Here two to_tsvector functions are being used and sum to one string because they use dwo different dicrtionaries
     * engish and simple to make results more reliable
     * Then function plainto_tsquery looks for similarities in provided key and those summer vectors.
     * This patter is used two times because ones plainto_tsquery uses english dictionary for and once simple dictionary
     * @param metadataBuilder spyfied in properties database details that builder is used for
     */

    @Override
    public void contribute(MetadataBuilder metadataBuilder) {
        metadataBuilder.applySqlFunction("fullSearch",
                new SQLFunctionTemplate(BooleanType.INSTANCE,
                         "owner_id = ?1 AND( to_tsvector('simple', search_name) || to_tsvector('english', search_name) @@ plainto_tsquery('english', ?2) OR to_tsvector('simple', search_name) || to_tsvector('english', search_name) @@ plainto_tsquery('simple', ?3))"));
    }
}
