# java-ship-it
Repository for homework project.

## Run tests

The project uses local `.jar` files from `lib/` and does not require Maven or Gradle.

From PowerShell, run all tests with:

```powershell
.\run-tests.ps1
```

Alternative launcher:

```powershell
.\run-tests.cmd
```

The script:
- compiles source files from `src/`
- compiles test files from `test/`
- runs all JUnit 5 tests
