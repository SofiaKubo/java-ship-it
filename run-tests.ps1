$ErrorActionPreference = "Stop"

$projectRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
$sourceOutput = Join-Path $projectRoot "out\production\java-ship-it"
$testOutput = Join-Path $projectRoot "out\test\java-ship-it"
$consoleLauncher = Join-Path $projectRoot "lib\junit-platform-console-standalone-1.4.2.jar"

if (-not (Test-Path $consoleLauncher)) {
    throw "Missing JUnit console launcher: $consoleLauncher"
}

New-Item -ItemType Directory -Force -Path $sourceOutput | Out-Null
New-Item -ItemType Directory -Force -Path $testOutput | Out-Null

$sourceFiles = Get-ChildItem -Path (Join-Path $projectRoot "src") -Recurse -Filter *.java |
        Select-Object -ExpandProperty FullName
$testFiles = Get-ChildItem -Path (Join-Path $projectRoot "test") -Recurse -Filter *.java |
        Select-Object -ExpandProperty FullName
$libraryFiles = Get-ChildItem -Path (Join-Path $projectRoot "lib") -Filter *.jar |
        Select-Object -ExpandProperty FullName

if ($sourceFiles.Count -eq 0) {
    throw "No source files found under src."
}

if ($testFiles.Count -eq 0) {
    throw "No test files found under test."
}

javac -encoding UTF-8 -cp "$projectRoot\lib\*" -d $sourceOutput $sourceFiles
javac -encoding UTF-8 -cp "$projectRoot\lib\*;$sourceOutput" -d $testOutput $testFiles

$runtimeClasspathEntries = @($sourceOutput, $testOutput) + $libraryFiles
$runtimeClasspath = [string]::Join(";", $runtimeClasspathEntries)

java -jar $consoleLauncher `
    --class-path $runtimeClasspath `
    --scan-class-path
