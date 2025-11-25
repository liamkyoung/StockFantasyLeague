package Stock.Fantasy.League.market.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Industry {

    AIR_FREIGHT("Air Freight"),
    ANIMAL_HEALTH("Animal Health"),
    APPAREL("Apparel"),
    AUTOMOBILES("Automobiles"),
    AUTOMATION("Automation"),
    AEROSPACE_DEFENSE("Aerospace & Defense"),
    BANKS("Banks"),
    BEVERAGES("Beverages"),
    BIOTECHNOLOGY("Biotechnology"),
    BUSINESS_SERVICES("Business Services"),
    CHEMICALS("Chemicals"),
    CONGLOMERATES("Conglomerates"),
    CONSUMER_ELECTRONICS("Consumer Electronics"),
    CREDIT_RATINGS("Credit Ratings"),
    DATA_CENTER_REITS("Data Center REITs"),
    DIVERSIFIED_HOLDINGS("Diversified Holdings"),
    DIVERSIFIED_INDUSTRIALS("Diversified Industrials"),
    ELECTRIC_UTILITIES("Electric Utilities"),
    ELECTRICAL_EQUIPMENT("Electrical Equipment"),
    ENTERPRISE_SOFTWARE("Enterprise Software"),
    ENTERTAINMENT("Entertainment"),
    ENVIRONMENTAL_SERVICES("Environmental Services"),
    FINANCIAL_DATA("Financial Data"),
    FINANCIAL_EXCHANGES("Financial Exchanges"),
    FINANCIAL_SERVICES("Financial Services"),
    FOOD_PRODUCTS("Food Products"),
    HOME_IMPROVEMENT("Home Improvement"),
    HOUSEHOLD_PRODUCTS("Household Products"),
    INDUSTRIAL_REITS("Industrial REITs"),
    INSURANCE("Insurance"),
    INSURANCE_BROKERS("Insurance Brokers"),
    INTERACTIVE_MEDIA("Interactive Media"),
    INTERNET_RETAIL("Internet Retail"),
    INVESTMENT_BANKING("Investment Banking"),
    IT_SERVICES("IT Services"),
    LIFE_SCIENCES_TOOLS("Life Sciences Tools & Services"),
    MACHINERY("Machinery"),
    MANAGED_HEALTH_CARE("Managed Health Care"),
    MEDICAL_DEVICES("Medical Devices"),
    NETWORKING("Networking"),
    OIL_GAS("Oil & Gas"),
    ONLINE_TRAVEL("Online Travel"),
    PAINTS_COATINGS("Paints & Coatings"),
    PHARMACEUTICALS("Pharmaceuticals"),
    RAILROADS("Railroads"),
    RESTAURANTS("Restaurants"),
    RETAIL("Retail"),
    SEMICONDUCTORS("Semiconductors"),
    SOCIAL_MEDIA("Social Media"),
    SOFTWARE("Software"),
    SYSTEMS_SOFTWARE("Systems Software"),
    TELECOM("Telecom"),
    TOBACCO("Tobacco"),
    TRAVEL_SERVICES("Travel Services"),
    UTILITIES("Electric Utilities"); // duplicate category handled above

    private final String label;

    public String getLabel() {
        return label;
    }

    /** Reverse lookup by human-readable label */
    public static Industry fromLabel(String label) {
        for (Industry i : values()) {
            if (i.label.equalsIgnoreCase(label)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Unknown industry label: " + label);
    }
}
