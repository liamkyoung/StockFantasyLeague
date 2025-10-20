package Stock.Fantasy.League.market.domain;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

public interface Stock {
    String id();
    String symbol();
    String name();
    Sector sector();
    String industry();
    long initPrice();
}