package Stock.Fantasy.League.market.infrastructure;

import Stock.Fantasy.League.market.domain.Quote;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.ZoneOffset;
import java.util.List;

@AllArgsConstructor
@Repository
public class QuoteBatchRepository {

    private static final String UPSERT_SQL = """
        INSERT INTO quotes_raw(symbol, price_in_cents, ts)
        VALUES (?, ?, ?)
        ON CONFLICT (symbol, ts) DO UPDATE
        SET price_in_cents = EXCLUDED.price_in_cents
        """;

    private final JdbcTemplate jdbcTemplate;


    public int[] saveAll(List<Quote> quotes) {
        if (quotes == null || quotes.isEmpty()) return new int[0];

        return jdbcTemplate.batchUpdate(UPSERT_SQL, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Quote q = quotes.get(i);
                ps.setString(1, q.symbol());
                ps.setLong(2, q.priceInCents());
                ps.setObject(3, q.ts().atOffset(ZoneOffset.UTC));
            }

            @Override
            public int getBatchSize() {
                return quotes.size();
            }
        });
    }
}