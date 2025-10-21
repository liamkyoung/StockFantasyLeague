CREATE TABLE IF NOT EXISTS quotes_raw (
      symbol           text        NOT NULL,
      price_in_cents   bigint      NOT NULL,
      ts               timestamptz NOT NULL,
      PRIMARY KEY (symbol, ts)
);

-- To get fast lookup of latest value of symbol  --
CREATE INDEX IF NOT EXISTS quotes_raw_ts_desc_idx
    ON quotes_raw (ts DESC);

-- For quick symbol lookups --
CREATE INDEX IF NOT EXISTS idx_quotes_symbol
    ON quotes_raw (symbol);