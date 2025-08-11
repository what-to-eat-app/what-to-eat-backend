LIQUIBASE_HOME := /opt/homebrew/opt/liquibase/libexec

update:
	$(LIQUIBASE_HOME)/liquibase  update

rollback:
	$(LIQUIBASE_HOME)/liquibase --url=$(DB_URL) \
								--username=$(DB_USERNAME) \
								--password=$(DB_PASSWORD) \
								--changelog-file=$(CHANGELOG_FILE) rollback -Dversion=1.0

generate-changelog:
	$(LIQUIBASE_HOME)/liquibase --url=$(DB_URL) \
								--username=$(DB_USERNAME) \
								--password=$(DB_PASSWORD) \
								--changelog-file=$(CHANGELOG_FILE) generate-changelog

diff:
	$(LIQUIBASE_HOME)/liquibase --url=$(DB_URL) \
								--username=$(DB_USERNAME) \
								--password=$(DB_PASSWORD) \
								--changelog-file=$(CHANGELOG_FILE) diff