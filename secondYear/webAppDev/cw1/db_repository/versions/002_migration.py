from sqlalchemy import *
from migrate import *


from migrate.changeset import schema
pre_meta = MetaData()
post_meta = MetaData()
property = Table('property', pre_meta,
    Column('id', INTEGER, primary_key=True, nullable=False),
    Column('address', VARCHAR(length=500)),
    Column('start_date', DATETIME),
    Column('duration', INTEGER),
    Column('rent', FLOAT),
)

property = Table('property', post_meta,
    Column('id', Integer, primary_key=True, nullable=False),
    Column('start_date', DateTime),
    Column('title', String(length=500)),
    Column('description', String(length=500)),
    Column('completed', Boolean),
)


def upgrade(migrate_engine):
    # Upgrade operations go here. Don't create your own engine; bind
    # migrate_engine to your metadata
    pre_meta.bind = migrate_engine
    post_meta.bind = migrate_engine
    pre_meta.tables['property'].columns['address'].drop()
    pre_meta.tables['property'].columns['duration'].drop()
    pre_meta.tables['property'].columns['rent'].drop()
    post_meta.tables['property'].columns['completed'].create()
    post_meta.tables['property'].columns['description'].create()
    post_meta.tables['property'].columns['title'].create()


def downgrade(migrate_engine):
    # Operations to reverse the above upgrade go here.
    pre_meta.bind = migrate_engine
    post_meta.bind = migrate_engine
    pre_meta.tables['property'].columns['address'].create()
    pre_meta.tables['property'].columns['duration'].create()
    pre_meta.tables['property'].columns['rent'].create()
    post_meta.tables['property'].columns['completed'].drop()
    post_meta.tables['property'].columns['description'].drop()
    post_meta.tables['property'].columns['title'].drop()
