package eccrm.utils.codeutils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author miles
 * @datetime 13-12-24 上午11:55
 */
public class Block {
    private List<Row> rows ;

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public void addRow(Row row) {
        if (rows == null) {
            rows = new ArrayList<Row>();
        }
        rows.add(row);
    }
}
