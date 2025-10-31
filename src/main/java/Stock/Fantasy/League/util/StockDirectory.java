package Stock.Fantasy.League.util;

import Stock.Fantasy.League.market.domain.BasicStock;
import Stock.Fantasy.League.market.domain.Sector;
import Stock.Fantasy.League.market.domain.Stock;
import org.springframework.stereotype.Component;
import java.util.*;

/**
 * In-memory registry of known stocks -- will later add to database -- in memory directory is fine for now.
 */
@Component
public class StockDirectory {

    private static final Map<String, Stock> bySymbol = new HashMap<>();

    public StockDirectory() {
        registerTopStocks();
    }

    private void registerTopStocks() {
        List<BasicStock> topStocks = List.of(
                    new BasicStock("1",  "AAPL",  "Apple Inc.",                   Sector.INFORMATION_TECHNOLOGY, "Consumer Electronics", 19555L),
                    new BasicStock("2",  "MSFT",  "Microsoft Corporation",        Sector.INFORMATION_TECHNOLOGY, "Systems Software",     42000L),
                    new BasicStock("3",  "AMZN",  "Amazon.com, Inc.",             Sector.CONSUMER_DISCRETIONARY, "Internet Retail",      17500L),
                    new BasicStock("4",  "GOOGL", "Alphabet Inc. (Class A)",      Sector.COMMUNICATION_SERVICES, "Interactive Media",    16500L),
                    new BasicStock("5",  "GOOG",  "Alphabet Inc. (Class C)",      Sector.COMMUNICATION_SERVICES, "Interactive Media",    16500L),
                    new BasicStock("6",  "META",  "Meta Platforms, Inc.",         Sector.COMMUNICATION_SERVICES, "Social Media",         48000L),
                    new BasicStock("7",  "NVDA",  "NVIDIA Corporation",           Sector.INFORMATION_TECHNOLOGY, "Semiconductors",       12500L),
                    new BasicStock("8",  "TSLA",  "Tesla, Inc.",                  Sector.CONSUMER_DISCRETIONARY, "Automobiles",          25000L),
                    new BasicStock("9",  "BRK.B", "Berkshire Hathaway Inc. B",    Sector.FINANCIALS,             "Diversified Holdings", 41200L),
                    new BasicStock("10", "UNH",   "UnitedHealth Group Inc.",      Sector.HEALTH_CARE,            "Managed Health Care",  52000L),
                    new BasicStock("11", "LLY",   "Eli Lilly and Company",        Sector.HEALTH_CARE,            "Pharmaceuticals",      86000L),
                    new BasicStock("12", "JNJ",   "Johnson & Johnson",            Sector.HEALTH_CARE,            "Pharmaceuticals",      15500L),
                    new BasicStock("13", "XOM",   "Exxon Mobil Corporation",      Sector.ENERGY,                 "Oil & Gas",            11500L),
                    new BasicStock("14", "JPM",   "JPMorgan Chase & Co.",         Sector.FINANCIALS,             "Banks",                20000L),
                    new BasicStock("15", "V",     "Visa Inc.",                    Sector.FINANCIALS,             "Financial Services",   26000L),
                    new BasicStock("16", "PG",    "Procter & Gamble Co.",         Sector.CONSUMER_STAPLES,       "Household Products",   15500L),
                    new BasicStock("17", "MA",    "Mastercard Incorporated",      Sector.FINANCIALS,             "Financial Services",   41000L),
                    new BasicStock("18", "HD",    "Home Depot, Inc.",             Sector.CONSUMER_DISCRETIONARY, "Home Improvement",     33000L),
                    new BasicStock("19", "CVX",   "Chevron Corporation",          Sector.ENERGY,                 "Oil & Gas",            14500L),
                    new BasicStock("20", "ABBV",  "AbbVie Inc.",                  Sector.HEALTH_CARE,            "Pharmaceuticals",      17000L),
                    new BasicStock("21", "MRK",   "Merck & Co., Inc.",            Sector.HEALTH_CARE,            "Pharmaceuticals",      13000L),
                    new BasicStock("22", "PEP",   "PepsiCo, Inc.",                Sector.CONSUMER_STAPLES,       "Beverages",            18000L),
                    new BasicStock("23", "KO",    "Coca-Cola Company",            Sector.CONSUMER_STAPLES,       "Beverages",             6000L),
                    new BasicStock("24", "COST",  "Costco Wholesale Corp.",       Sector.CONSUMER_STAPLES,       "Retail",               72000L),
                    new BasicStock("25", "AVGO",  "Broadcom Inc.",                Sector.INFORMATION_TECHNOLOGY, "Semiconductors",       14500L),
                    new BasicStock("26", "ADBE",  "Adobe Inc.",                   Sector.INFORMATION_TECHNOLOGY, "Software",             54000L),
                    new BasicStock("27", "TMO",   "Thermo Fisher Scientific",     Sector.HEALTH_CARE,            "Life Sciences Tools",  50000L),
                    new BasicStock("28", "NFLX",  "Netflix, Inc.",                Sector.COMMUNICATION_SERVICES, "Entertainment",        58000L),
                    new BasicStock("29", "WMT",   "Walmart Inc.",                 Sector.CONSUMER_STAPLES,       "Retail",                6700L),
                    new BasicStock("30", "DIS",   "Walt Disney Company",          Sector.COMMUNICATION_SERVICES, "Entertainment",         9500L),
                    new BasicStock("31", "CRM",   "Salesforce, Inc.",             Sector.INFORMATION_TECHNOLOGY, "Enterprise Software",  27000L),
                    new BasicStock("32", "INTC",  "Intel Corporation",            Sector.INFORMATION_TECHNOLOGY, "Semiconductors",        3500L),
                    new BasicStock("33", "CSCO",  "Cisco Systems, Inc.",          Sector.INFORMATION_TECHNOLOGY, "Networking",            4800L),
                    new BasicStock("34", "NKE",   "Nike, Inc.",                   Sector.CONSUMER_DISCRETIONARY, "Apparel",               9000L),
                    new BasicStock("35", "ORCL",  "Oracle Corporation",           Sector.INFORMATION_TECHNOLOGY, "Software",             13000L),
                    new BasicStock("36", "AMD",   "Advanced Micro Devices",       Sector.INFORMATION_TECHNOLOGY, "Semiconductors",       12000L),
                    new BasicStock("37", "MCD",   "McDonald's Corporation",       Sector.CONSUMER_DISCRETIONARY, "Restaurants",          27000L),
                    new BasicStock("38", "UPS",   "United Parcel Service, Inc.",  Sector.INDUSTRIALS,            "Air Freight",          15000L),
                    new BasicStock("39", "QCOM",  "Qualcomm Inc.",                Sector.INFORMATION_TECHNOLOGY, "Semiconductors",       14500L),
                    new BasicStock("40", "HON",   "Honeywell International Inc.", Sector.INDUSTRIALS,            "Conglomerates",        19000L),
                    new BasicStock("41", "TXN",   "Texas Instruments Inc.",       Sector.INFORMATION_TECHNOLOGY, "Semiconductors",       16500L),
                    new BasicStock("42", "BMY",   "Bristol-Myers Squibb Co.",     Sector.HEALTH_CARE,            "Pharmaceuticals",       5000L),
                    new BasicStock("43", "RTX",   "RTX Corporation",              Sector.INDUSTRIALS,            "Aerospace & Defense",   9500L),
                    new BasicStock("44", "NEE",   "NextEra Energy, Inc.",         Sector.UTILITIES,              "Electric Utilities",    6500L),
                    new BasicStock("45", "UNP",   "Union Pacific Corporation",    Sector.INDUSTRIALS,            "Railroads",            23000L),
                    new BasicStock("46", "MS",    "Morgan Stanley",               Sector.FINANCIALS,             "Investment Banking",    9000L),
                    new BasicStock("47", "GS",    "Goldman Sachs Group, Inc.",    Sector.FINANCIALS,             "Investment Banking",   41000L),
                    new BasicStock("48", "IBM",   "International Business Machines", Sector.INFORMATION_TECHNOLOGY, "IT Services",       18000L),
                    new BasicStock("49", "CAT",   "Caterpillar Inc.",             Sector.INDUSTRIALS,            "Machinery",            29000L),
                    new BasicStock("50", "GE",    "General Electric Company",     Sector.INDUSTRIALS,            "Diversified Industrials", 13500L),
                    new BasicStock("51", "SBUX",  "Starbucks Corporation",        Sector.CONSUMER_DISCRETIONARY, "Restaurants",           9500L),
                    new BasicStock("52", "LMT",   "Lockheed Martin Corporation",  Sector.INDUSTRIALS,            "Aerospace & Defense",  44000L),
                    new BasicStock("53", "DE",    "Deere & Company",              Sector.INDUSTRIALS,            "Machinery",            38000L),
                    new BasicStock("54", "NOW",   "ServiceNow, Inc.",             Sector.INFORMATION_TECHNOLOGY, "Software",             70000L),
                    new BasicStock("55", "MDT",   "Medtronic plc",                Sector.HEALTH_CARE,            "Medical Devices",       8500L),
                    new BasicStock("56", "BKNG",  "Booking Holdings Inc.",        Sector.CONSUMER_DISCRETIONARY, "Travel Services",     350000L),
                    new BasicStock("57", "ADP",   "Automatic Data Processing",    Sector.INFORMATION_TECHNOLOGY, "Business Services",    26000L),
                    new BasicStock("58", "GILD",  "Gilead Sciences, Inc.",        Sector.HEALTH_CARE,            "Biotechnology",         7600L),
                    new BasicStock("59", "MDLZ",  "Mondelez International",       Sector.CONSUMER_STAPLES,       "Food Products",         7200L),
                    new BasicStock("60", "SO",    "Southern Company",             Sector.UTILITIES,              "Electric Utilities",    7300L),
                    new BasicStock("61", "REGN",  "Regeneron Pharmaceuticals",    Sector.HEALTH_CARE,            "Biotechnology",        97000L),
                    new BasicStock("62", "CB",    "Chubb Limited",                Sector.FINANCIALS,             "Insurance",            24000L),
                    new BasicStock("63", "PLD",   "Prologis, Inc.",               Sector.REAL_ESTATE,            "Industrial REITs",     12000L),
                    new BasicStock("64", "ELV",   "Elevance Health, Inc.",        Sector.HEALTH_CARE,            "Managed Health Care",  53000L),
                    new BasicStock("65", "SYK",   "Stryker Corporation",          Sector.HEALTH_CARE,            "Medical Devices",      34500L),
                    new BasicStock("66", "T",     "AT&T Inc.",                    Sector.COMMUNICATION_SERVICES, "Telecom",               1800L),
                    new BasicStock("67", "MO",    "Altria Group, Inc.",           Sector.CONSUMER_STAPLES,       "Tobacco",               4300L),
                    new BasicStock("68", "VRTX",  "Vertex Pharmaceuticals",       Sector.HEALTH_CARE,            "Biotechnology",        46500L),
                    new BasicStock("69", "TJX",   "TJX Companies, Inc.",          Sector.CONSUMER_DISCRETIONARY, "Retail",               10400L),
                    new BasicStock("70", "C",     "Citigroup Inc.",               Sector.FINANCIALS,             "Banks",                 6200L),
                    new BasicStock("71", "MMC",   "Marsh & McLennan Cos.",        Sector.FINANCIALS,             "Insurance Brokers",    23000L),
                    new BasicStock("72", "SPGI",  "S&P Global Inc.",              Sector.FINANCIALS,             "Financial Data",       46000L),
                    new BasicStock("73", "PNC",   "PNC Financial Services",       Sector.FINANCIALS,             "Banks",                14000L),
                    new BasicStock("74", "COP",   "ConocoPhillips",               Sector.ENERGY,                 "Oil & Gas",            12000L),
                    new BasicStock("75", "AMAT",  "Applied Materials, Inc.",      Sector.INFORMATION_TECHNOLOGY, "Semiconductors",       22000L),
                    new BasicStock("76", "ISRG",  "Intuitive Surgical, Inc.",     Sector.HEALTH_CARE,            "Medical Devices",      42000L),
                    new BasicStock("77", "DUK",   "Duke Energy Corp.",            Sector.UTILITIES,              "Electric Utilities",    8800L),
                    new BasicStock("78", "EQIX",  "Equinix, Inc.",                Sector.REAL_ESTATE,            "Data Center REITs",    85000L),
                    new BasicStock("79", "ZTS",   "Zoetis Inc.",                  Sector.HEALTH_CARE,            "Animal Health",        19500L),
                    new BasicStock("80", "BSX",   "Boston Scientific Corp.",      Sector.HEALTH_CARE,            "Medical Devices",       8300L),
                    new BasicStock("81", "EOG",   "EOG Resources, Inc.",          Sector.ENERGY,                 "Oil & Gas",            13000L),
                    new BasicStock("82", "CL",    "Colgate-Palmolive Company",    Sector.CONSUMER_STAPLES,       "Household Products",    8000L),
                    new BasicStock("83", "CI",    "Cigna Group",                  Sector.HEALTH_CARE,            "Managed Health Care",  34000L),
                    new BasicStock("84", "BDX",   "Becton, Dickinson and Co.",    Sector.HEALTH_CARE,            "Medical Devices",      23500L),
                    new BasicStock("85", "USB",   "U.S. Bancorp",                 Sector.FINANCIALS,             "Banks",                 4400L),
                    new BasicStock("86", "ABNB",  "Airbnb, Inc.",                 Sector.CONSUMER_DISCRETIONARY, "Online Travel",        13000L),
                    new BasicStock("87", "ETN",   "Eaton Corporation",            Sector.INDUSTRIALS,            "Electrical Equipment", 32000L),
                    new BasicStock("88", "WM",    "Waste Management, Inc.",       Sector.INDUSTRIALS,            "Environmental Services", 19000L),
                    new BasicStock("89", "APD",   "Air Products & Chemicals",     Sector.MATERIALS,              "Chemicals",            27000L),
                    new BasicStock("90", "PSX",   "Phillips 66",                  Sector.ENERGY,                 "Oil & Gas",            12000L),
                    new BasicStock("91", "TRV",   "Travelers Companies, Inc.",    Sector.FINANCIALS,             "Insurance",            22000L),
                    new BasicStock("92", "FDX",   "FedEx Corporation",            Sector.INDUSTRIALS,            "Air Freight",          24000L),
                    new BasicStock("93", "CME",   "CME Group Inc.",               Sector.FINANCIALS,             "Financial Exchanges",  21000L),
                    new BasicStock("94", "ADSK",  "Autodesk, Inc.",               Sector.INFORMATION_TECHNOLOGY, "Software",             24500L),
                    new BasicStock("95", "SHW",   "Sherwin-Williams Co.",         Sector.MATERIALS,              "Paints & Coatings",    31000L),
                    new BasicStock("96", "EMR",   "Emerson Electric Co.",         Sector.INDUSTRIALS,            "Automation",           10500L),
                    new BasicStock("97", "MCO",   "Moody's Corporation",          Sector.FINANCIALS,             "Credit Ratings",       43000L),
                    new BasicStock("98", "GM",    "General Motors Company",       Sector.CONSUMER_DISCRETIONARY, "Automobiles",           3500L),
                    new BasicStock("99", "CSX",   "CSX Corporation",              Sector.INDUSTRIALS,            "Railroads",             3400L),
                    new BasicStock("100","ADI",   "Analog Devices, Inc.",         Sector.INFORMATION_TECHNOLOGY, "Semiconductors",       19000L)
        );

        topStocks.forEach(stock -> bySymbol.put(stock.symbol(), stock));
    }


    public static Optional<Stock> find(String symbol) {
        if (symbol == null) return Optional.empty();
        return Optional.ofNullable(bySymbol.get(symbol.toUpperCase(Locale.ROOT)));
    }

    public static boolean isValidSymbol(String symbol) {
        return find(symbol).isPresent();
    }

    public static Collection<Stock> all() {
        return Collections.unmodifiableCollection(bySymbol.values());
    }

    public static Collection<String> allSymbols() {
        return Collections.unmodifiableCollection(bySymbol.values().stream().map(Stock::symbol).toList());
    }
}