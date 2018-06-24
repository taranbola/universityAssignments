from sqlalchemy import *
from migrate import *


from migrate.changeset import schema
pre_meta = MetaData()
post_meta = MetaData()
landlord = Table('landlord', post_meta,
    Column('id', Integer, primary_key=True, nullable=False),
    Column('name', String(length=500)),
    Column('contact_number', String(length=20)),
    Column('address', String(length=500)),
)

property = Table('property', post_meta,
    Column('id', Integer, primary_key=True, nullable=False),
    Column('address', String(length=500)),
    Column('start_date', DateTime),
    Column('duration', Integer),
    Column('rent', Float),
    Column('landlord_id', Integer),
)


def upgrade(migrate_engine):
    # Upgrade operations go here. Don't create your own engine; bind
    # migrate_engine to your metadata
    pre_meta.bind = migrate_engine
    post_meta.bind = migrate_engine
    post_meta.tables['landlord'].create()
    post_meta.tables['property'].create()


def downgrade(migrate_engine):
    # Operations to reverse the above upgrade go here.
    pre_meta.bind = migrate_engine
    post_meta.bind = migrate_engine
    post_meta.tables['landlord'].drop()
    post_meta.tables['property'].drop()
