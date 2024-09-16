CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE employee (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    username VARCHAR(50) UNIQUE NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TYPE organization_type AS ENUM (
    'IE',
    'LLC',
    'JSC'
);

CREATE TABLE organization (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(100) NOT NULL,
    description TEXT,
    type organization_type,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE organization_responsible (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    organization_id UUID REFERENCES organization(id) ON DELETE CASCADE,
    user_id UUID REFERENCES employee(id) ON DELETE CASCADE
);

CREATE TABLE tenders (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(100) NOT NULL,
    description TEXT,
    serviceType VARCHAR(100),
    status VARCHAR(50),
    organizationId UUID REFERENCES organization(id),
    creatorUsername VARCHAR(50) REFERENCES employee(username) NOT NULL,
    version INTEGER
);

CREATE TABLE tender_versions (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(100) NOT NULL,
    description TEXT,
    version INTEGER,
    tenderId UUID REFERENCES tenders(id)
);

CREATE TABLE bids (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(100) NOT NULL,
    description TEXT,
    status VARCHAR(50),
    tenderId UUID REFERENCES tenders(id),
    organizationId UUID REFERENCES organization(id),
    creatorUsername VARCHAR(50) REFERENCES employee(username) NOT NULL,
    version INTEGER
);

CREATE TABLE bid_versions (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(100) NOT NULL,
    description TEXT,
    version INTEGER,
    bidId UUID REFERENCES bids(id)
);

CREATE TABLE bid_approval (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    bidId UUID REFERENCES bids(id),
    quorum INTEGER,
    approvedNumber INTEGER
);

CREATE TABLE feedback (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    description TEXT,
    username VARCHAR(50) REFERENCES employee(username) NOT NULL,
    bidId UUID REFERENCES bids(id)
);