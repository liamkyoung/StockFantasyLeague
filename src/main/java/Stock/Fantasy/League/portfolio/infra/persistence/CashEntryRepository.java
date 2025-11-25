package Stock.Fantasy.League.portfolio.infra.persistence;

import Stock.Fantasy.League.portfolio.domain.CashEntry;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.UUID;

public interface CashEntryRepository extends JpaRepository<CashEntry, UUID> {

    @Query("""
               select coalesce(sum(c.amountInCents),0)
               from CashEntry c
               where c.leagueUser.leagueUserId = :luId
                 and c.timestamp <= :asOf
            """)
    long currentBalance(@Param("luId") UUID leagueUserId,
                     @Param("asOf") Instant asOf);
}