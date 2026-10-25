# Contributing

Open an issue before a substantial change. Create a focused branch from `main`, keep credentials and local data out of commits, and run `mvn --batch-mode verify` before opening a pull request. Include tests and documentation for changed behaviour.

Bug reports should contain reproducible steps, expected and actual behaviour, and sanitized environment details. Report vulnerabilities privately as described in [SECURITY.md](SECURITY.md).

Database and file-storage settings can be supplied with `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `IMAGE_UPLOAD_HOME`, and `STATIC_DOMAIN`. Never commit real credentials or machine-specific paths.
