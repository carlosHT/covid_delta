package com.herreratreib.gcp.covid.delta.config;

import com.google.api.services.bigquery.model.TableRow;
import org.apache.beam.sdk.coders.Coder;
import org.apache.beam.sdk.coders.CustomCoder;
import org.apache.beam.sdk.io.gcp.bigquery.TableRowJsonCoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TableRowCoder extends CustomCoder<TableRow> {
    private static final Coder<TableRow> TABLE_ROW_CODER = TableRowJsonCoder.of();

    @Override
    public void encode(TableRow value,
                       OutputStream outStream) throws IOException {
        TABLE_ROW_CODER.encode(value,
                               outStream);
    }

    @Override
    public TableRow decode(InputStream inStream) throws IOException {
        return TABLE_ROW_CODER.decode(inStream);
    }
}
