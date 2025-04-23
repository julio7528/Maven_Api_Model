# Estrutura de Pastas e Arquivos

- `./`
    - `.gitattributes`
    - `.gitignore`
    - `docker-compose.yml`
    - `Dockerfile`
    - `HELP.md`
    - `mvnw`
    - `mvnw.cmd`
    - `pom.xml`
    - `README.md`
    - `.mvn/`
        - `wrapper/`
            - `maven-wrapper.properties`
    - `src/`
        - `main/`
            - `java/`
                - `br/`
                    - `com/`
                        - `cadastrocarros/`
                            - `CadastroCarrosApplication.java`
                            - `controller/`
                                - `CarroController.java`
                            - `model/`
                                - `Carro.java`
                            - `repository/`
                                - `CarroRepository.java`
            - `resources/`
                - `application.properties`
        - `test/`
            - `java/`
                - `br/`
                    - `com/`
                        - `cadastrocarros/`
                            - `CadastroCarrosApplicationTests.java`


# Conteúdo dos Arquivos

---

## Caminho: `.gitattributes`

``` 
/mvnw text eol=lf
*.cmd text eol=crlf

```

---

## Caminho: `.gitignore`

``` 
HELP.md
target/
!.mvn/wrapper/maven-wrapper.jar
!**/src/main/**/target/
!**/src/test/**/target/

### STS ###
.apt_generated
.classpath
.factorypath
.project
.settings
.springBeans
.sts4-cache

### IntelliJ IDEA ###
.idea
*.iws
*.iml
*.ipr

### NetBeans ###
/nbproject/private/
/nbbuild/
/dist/
/nbdist/
/.nb-gradle/
build/
!**/src/main/**/build/
!**/src/test/**/build/

### VS Code ###
.vscode/

```

---

## Caminho: `docker-compose.yml`

``` yaml
version: '3.8'

services:
  cadastro-carros-app:
    build: .
    container_name: cadastro-carros-api
    ports:
      - "8087:8087"
    environment:
      # Se precisar passar variáveis de ambiente para a aplicação Spring Boot,
      # descomente e ajuste as linhas abaixo.
      # Exemplo:
      # - SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/mydatabase
      # - SPRING_DATASOURCE_USERNAME=user
      # - SPRING_DATASOURCE_PASSWORD=password
      - SERVER_PORT=8087 # Garante que a porta interna seja a mesma exposta

# Se você tivesse um banco de dados externo (ex: PostgreSQL) em outro container,
# você o definiria aqui e poderia usar 'depends_on' no serviço 'cadastro-carros-app'.
# Exemplo:
# services:
#   db:
#     image: postgres:14
#     container_name: postgres_db
#     environment:
#       POSTGRES_DB: mydatabase
#       POSTGRES_USER: user
#       POSTGRES_PASSWORD: password
#     volumes:
#       - postgres_data:/var/lib/postgresql/data
#     ports:
#       - "5432:5432"
#
#   cadastro-carros-app:
#     build: .
#     container_name: cadastro-carros-api
#     ports:
#       - "8087:8087"
#     depends_on:
#       - db
#     environment:
#       - SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/mydatabase
#       - SPRING_DATASOURCE_USERNAME=user
#       - SPRING_DATASOURCE_PASSWORD=password
#       - SERVER_PORT=8087
#
# volumes:
#   postgres_data:
```

---

## Caminho: `Dockerfile`

``` 
# Estágio 1: Build da aplicação com Maven
FROM maven:3.8.5-openjdk-17 AS build

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o pom.xml para baixar as dependências primeiro (cache)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o restante do código fonte
COPY src ./src

# Compila e empacota a aplicação
RUN mvn package -DskipTests

# Estágio 2: Criação da imagem final com JRE
FROM openjdk:17-jre-slim

# Define o diretório de trabalho
WORKDIR /app

# Copia o JAR da aplicação do estágio de build
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta que a aplicação usa (conforme application.properties)
EXPOSE 8087

# Comando para executar a aplicação quando o container iniciar
ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

## Caminho: `HELP.md`

``` markdown
# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.4/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.4.4/maven-plugin/build-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.4.4/reference/web/servlet.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/3.4.4/reference/data/sql.html#data.sql.jpa-and-spring-data)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/3.4.4/reference/using/devtools.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.


```

---

## Caminho: `mvnw`

``` 
#!/bin/sh
# ----------------------------------------------------------------------------
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.
# ----------------------------------------------------------------------------

# ----------------------------------------------------------------------------
# Apache Maven Wrapper startup batch script, version 3.3.2
#
# Optional ENV vars
# -----------------
#   JAVA_HOME - location of a JDK home dir, required when download maven via java source
#   MVNW_REPOURL - repo url base for downloading maven distribution
#   MVNW_USERNAME/MVNW_PASSWORD - user and password for downloading maven
#   MVNW_VERBOSE - true: enable verbose log; debug: trace the mvnw script; others: silence the output
# ----------------------------------------------------------------------------

set -euf
[ "${MVNW_VERBOSE-}" != debug ] || set -x

# OS specific support.
native_path() { printf %s\\n "$1"; }
case "$(uname)" in
CYGWIN* | MINGW*)
  [ -z "${JAVA_HOME-}" ] || JAVA_HOME="$(cygpath --unix "$JAVA_HOME")"
  native_path() { cygpath --path --windows "$1"; }
  ;;
esac

# set JAVACMD and JAVACCMD
set_java_home() {
  # For Cygwin and MinGW, ensure paths are in Unix format before anything is touched
  if [ -n "${JAVA_HOME-}" ]; then
    if [ -x "$JAVA_HOME/jre/sh/java" ]; then
      # IBM's JDK on AIX uses strange locations for the executables
      JAVACMD="$JAVA_HOME/jre/sh/java"
      JAVACCMD="$JAVA_HOME/jre/sh/javac"
    else
      JAVACMD="$JAVA_HOME/bin/java"
      JAVACCMD="$JAVA_HOME/bin/javac"

      if [ ! -x "$JAVACMD" ] || [ ! -x "$JAVACCMD" ]; then
        echo "The JAVA_HOME environment variable is not defined correctly, so mvnw cannot run." >&2
        echo "JAVA_HOME is set to \"$JAVA_HOME\", but \"\$JAVA_HOME/bin/java\" or \"\$JAVA_HOME/bin/javac\" does not exist." >&2
        return 1
      fi
    fi
  else
    JAVACMD="$(
      'set' +e
      'unset' -f command 2>/dev/null
      'command' -v java
    )" || :
    JAVACCMD="$(
      'set' +e
      'unset' -f command 2>/dev/null
      'command' -v javac
    )" || :

    if [ ! -x "${JAVACMD-}" ] || [ ! -x "${JAVACCMD-}" ]; then
      echo "The java/javac command does not exist in PATH nor is JAVA_HOME set, so mvnw cannot run." >&2
      return 1
    fi
  fi
}

# hash string like Java String::hashCode
hash_string() {
  str="${1:-}" h=0
  while [ -n "$str" ]; do
    char="${str%"${str#?}"}"
    h=$(((h * 31 + $(LC_CTYPE=C printf %d "'$char")) % 4294967296))
    str="${str#?}"
  done
  printf %x\\n $h
}

verbose() { :; }
[ "${MVNW_VERBOSE-}" != true ] || verbose() { printf %s\\n "${1-}"; }

die() {
  printf %s\\n "$1" >&2
  exit 1
}

trim() {
  # MWRAPPER-139:
  #   Trims trailing and leading whitespace, carriage returns, tabs, and linefeeds.
  #   Needed for removing poorly interpreted newline sequences when running in more
  #   exotic environments such as mingw bash on Windows.
  printf "%s" "${1}" | tr -d '[:space:]'
}

# parse distributionUrl and optional distributionSha256Sum, requires .mvn/wrapper/maven-wrapper.properties
while IFS="=" read -r key value; do
  case "${key-}" in
  distributionUrl) distributionUrl=$(trim "${value-}") ;;
  distributionSha256Sum) distributionSha256Sum=$(trim "${value-}") ;;
  esac
done <"${0%/*}/.mvn/wrapper/maven-wrapper.properties"
[ -n "${distributionUrl-}" ] || die "cannot read distributionUrl property in ${0%/*}/.mvn/wrapper/maven-wrapper.properties"

case "${distributionUrl##*/}" in
maven-mvnd-*bin.*)
  MVN_CMD=mvnd.sh _MVNW_REPO_PATTERN=/maven/mvnd/
  case "${PROCESSOR_ARCHITECTURE-}${PROCESSOR_ARCHITEW6432-}:$(uname -a)" in
  *AMD64:CYGWIN* | *AMD64:MINGW*) distributionPlatform=windows-amd64 ;;
  :Darwin*x86_64) distributionPlatform=darwin-amd64 ;;
  :Darwin*arm64) distributionPlatform=darwin-aarch64 ;;
  :Linux*x86_64*) distributionPlatform=linux-amd64 ;;
  *)
    echo "Cannot detect native platform for mvnd on $(uname)-$(uname -m), use pure java version" >&2
    distributionPlatform=linux-amd64
    ;;
  esac
  distributionUrl="${distributionUrl%-bin.*}-$distributionPlatform.zip"
  ;;
maven-mvnd-*) MVN_CMD=mvnd.sh _MVNW_REPO_PATTERN=/maven/mvnd/ ;;
*) MVN_CMD="mvn${0##*/mvnw}" _MVNW_REPO_PATTERN=/org/apache/maven/ ;;
esac

# apply MVNW_REPOURL and calculate MAVEN_HOME
# maven home pattern: ~/.m2/wrapper/dists/{apache-maven-<version>,maven-mvnd-<version>-<platform>}/<hash>
[ -z "${MVNW_REPOURL-}" ] || distributionUrl="$MVNW_REPOURL$_MVNW_REPO_PATTERN${distributionUrl#*"$_MVNW_REPO_PATTERN"}"
distributionUrlName="${distributionUrl##*/}"
distributionUrlNameMain="${distributionUrlName%.*}"
distributionUrlNameMain="${distributionUrlNameMain%-bin}"
MAVEN_USER_HOME="${MAVEN_USER_HOME:-${HOME}/.m2}"
MAVEN_HOME="${MAVEN_USER_HOME}/wrapper/dists/${distributionUrlNameMain-}/$(hash_string "$distributionUrl")"

exec_maven() {
  unset MVNW_VERBOSE MVNW_USERNAME MVNW_PASSWORD MVNW_REPOURL || :
  exec "$MAVEN_HOME/bin/$MVN_CMD" "$@" || die "cannot exec $MAVEN_HOME/bin/$MVN_CMD"
}

if [ -d "$MAVEN_HOME" ]; then
  verbose "found existing MAVEN_HOME at $MAVEN_HOME"
  exec_maven "$@"
fi

case "${distributionUrl-}" in
*?-bin.zip | *?maven-mvnd-?*-?*.zip) ;;
*) die "distributionUrl is not valid, must match *-bin.zip or maven-mvnd-*.zip, but found '${distributionUrl-}'" ;;
esac

# prepare tmp dir
if TMP_DOWNLOAD_DIR="$(mktemp -d)" && [ -d "$TMP_DOWNLOAD_DIR" ]; then
  clean() { rm -rf -- "$TMP_DOWNLOAD_DIR"; }
  trap clean HUP INT TERM EXIT
else
  die "cannot create temp dir"
fi

mkdir -p -- "${MAVEN_HOME%/*}"

# Download and Install Apache Maven
verbose "Couldn't find MAVEN_HOME, downloading and installing it ..."
verbose "Downloading from: $distributionUrl"
verbose "Downloading to: $TMP_DOWNLOAD_DIR/$distributionUrlName"

# select .zip or .tar.gz
if ! command -v unzip >/dev/null; then
  distributionUrl="${distributionUrl%.zip}.tar.gz"
  distributionUrlName="${distributionUrl##*/}"
fi

# verbose opt
__MVNW_QUIET_WGET=--quiet __MVNW_QUIET_CURL=--silent __MVNW_QUIET_UNZIP=-q __MVNW_QUIET_TAR=''
[ "${MVNW_VERBOSE-}" != true ] || __MVNW_QUIET_WGET='' __MVNW_QUIET_CURL='' __MVNW_QUIET_UNZIP='' __MVNW_QUIET_TAR=v

# normalize http auth
case "${MVNW_PASSWORD:+has-password}" in
'') MVNW_USERNAME='' MVNW_PASSWORD='' ;;
has-password) [ -n "${MVNW_USERNAME-}" ] || MVNW_USERNAME='' MVNW_PASSWORD='' ;;
esac

if [ -z "${MVNW_USERNAME-}" ] && command -v wget >/dev/null; then
  verbose "Found wget ... using wget"
  wget ${__MVNW_QUIET_WGET:+"$__MVNW_QUIET_WGET"} "$distributionUrl" -O "$TMP_DOWNLOAD_DIR/$distributionUrlName" || die "wget: Failed to fetch $distributionUrl"
elif [ -z "${MVNW_USERNAME-}" ] && command -v curl >/dev/null; then
  verbose "Found curl ... using curl"
  curl ${__MVNW_QUIET_CURL:+"$__MVNW_QUIET_CURL"} -f -L -o "$TMP_DOWNLOAD_DIR/$distributionUrlName" "$distributionUrl" || die "curl: Failed to fetch $distributionUrl"
elif set_java_home; then
  verbose "Falling back to use Java to download"
  javaSource="$TMP_DOWNLOAD_DIR/Downloader.java"
  targetZip="$TMP_DOWNLOAD_DIR/$distributionUrlName"
  cat >"$javaSource" <<-END
	public class Downloader extends java.net.Authenticator
	{
	  protected java.net.PasswordAuthentication getPasswordAuthentication()
	  {
	    return new java.net.PasswordAuthentication( System.getenv( "MVNW_USERNAME" ), System.getenv( "MVNW_PASSWORD" ).toCharArray() );
	  }
	  public static void main( String[] args ) throws Exception
	  {
	    setDefault( new Downloader() );
	    java.nio.file.Files.copy( java.net.URI.create( args[0] ).toURL().openStream(), java.nio.file.Paths.get( args[1] ).toAbsolutePath().normalize() );
	  }
	}
	END
  # For Cygwin/MinGW, switch paths to Windows format before running javac and java
  verbose " - Compiling Downloader.java ..."
  "$(native_path "$JAVACCMD")" "$(native_path "$javaSource")" || die "Failed to compile Downloader.java"
  verbose " - Running Downloader.java ..."
  "$(native_path "$JAVACMD")" -cp "$(native_path "$TMP_DOWNLOAD_DIR")" Downloader "$distributionUrl" "$(native_path "$targetZip")"
fi

# If specified, validate the SHA-256 sum of the Maven distribution zip file
if [ -n "${distributionSha256Sum-}" ]; then
  distributionSha256Result=false
  if [ "$MVN_CMD" = mvnd.sh ]; then
    echo "Checksum validation is not supported for maven-mvnd." >&2
    echo "Please disable validation by removing 'distributionSha256Sum' from your maven-wrapper.properties." >&2
    exit 1
  elif command -v sha256sum >/dev/null; then
    if echo "$distributionSha256Sum  $TMP_DOWNLOAD_DIR/$distributionUrlName" | sha256sum -c >/dev/null 2>&1; then
      distributionSha256Result=true
    fi
  elif command -v shasum >/dev/null; then
    if echo "$distributionSha256Sum  $TMP_DOWNLOAD_DIR/$distributionUrlName" | shasum -a 256 -c >/dev/null 2>&1; then
      distributionSha256Result=true
    fi
  else
    echo "Checksum validation was requested but neither 'sha256sum' or 'shasum' are available." >&2
    echo "Please install either command, or disable validation by removing 'distributionSha256Sum' from your maven-wrapper.properties." >&2
    exit 1
  fi
  if [ $distributionSha256Result = false ]; then
    echo "Error: Failed to validate Maven distribution SHA-256, your Maven distribution might be compromised." >&2
    echo "If you updated your Maven version, you need to update the specified distributionSha256Sum property." >&2
    exit 1
  fi
fi

# unzip and move
if command -v unzip >/dev/null; then
  unzip ${__MVNW_QUIET_UNZIP:+"$__MVNW_QUIET_UNZIP"} "$TMP_DOWNLOAD_DIR/$distributionUrlName" -d "$TMP_DOWNLOAD_DIR" || die "failed to unzip"
else
  tar xzf${__MVNW_QUIET_TAR:+"$__MVNW_QUIET_TAR"} "$TMP_DOWNLOAD_DIR/$distributionUrlName" -C "$TMP_DOWNLOAD_DIR" || die "failed to untar"
fi
printf %s\\n "$distributionUrl" >"$TMP_DOWNLOAD_DIR/$distributionUrlNameMain/mvnw.url"
mv -- "$TMP_DOWNLOAD_DIR/$distributionUrlNameMain" "$MAVEN_HOME" || [ -d "$MAVEN_HOME" ] || die "fail to move MAVEN_HOME"

clean || :
exec_maven "$@"

```

---

## Caminho: `mvnw.cmd`

``` batch
<# : batch portion
@REM ----------------------------------------------------------------------------
@REM Licensed to the Apache Software Foundation (ASF) under one
@REM or more contributor license agreements.  See the NOTICE file
@REM distributed with this work for additional information
@REM regarding copyright ownership.  The ASF licenses this file
@REM to you under the Apache License, Version 2.0 (the
@REM "License"); you may not use this file except in compliance
@REM with the License.  You may obtain a copy of the License at
@REM
@REM    http://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing,
@REM software distributed under the License is distributed on an
@REM "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
@REM KIND, either express or implied.  See the License for the
@REM specific language governing permissions and limitations
@REM under the License.
@REM ----------------------------------------------------------------------------

@REM ----------------------------------------------------------------------------
@REM Apache Maven Wrapper startup batch script, version 3.3.2
@REM
@REM Optional ENV vars
@REM   MVNW_REPOURL - repo url base for downloading maven distribution
@REM   MVNW_USERNAME/MVNW_PASSWORD - user and password for downloading maven
@REM   MVNW_VERBOSE - true: enable verbose log; others: silence the output
@REM ----------------------------------------------------------------------------

@IF "%__MVNW_ARG0_NAME__%"=="" (SET __MVNW_ARG0_NAME__=%~nx0)
@SET __MVNW_CMD__=
@SET __MVNW_ERROR__=
@SET __MVNW_PSMODULEP_SAVE=%PSModulePath%
@SET PSModulePath=
@FOR /F "usebackq tokens=1* delims==" %%A IN (`powershell -noprofile "& {$scriptDir='%~dp0'; $script='%__MVNW_ARG0_NAME__%'; icm -ScriptBlock ([Scriptblock]::Create((Get-Content -Raw '%~f0'))) -NoNewScope}"`) DO @(
  IF "%%A"=="MVN_CMD" (set __MVNW_CMD__=%%B) ELSE IF "%%B"=="" (echo %%A) ELSE (echo %%A=%%B)
)
@SET PSModulePath=%__MVNW_PSMODULEP_SAVE%
@SET __MVNW_PSMODULEP_SAVE=
@SET __MVNW_ARG0_NAME__=
@SET MVNW_USERNAME=
@SET MVNW_PASSWORD=
@IF NOT "%__MVNW_CMD__%"=="" (%__MVNW_CMD__% %*)
@echo Cannot start maven from wrapper >&2 && exit /b 1
@GOTO :EOF
: end batch / begin powershell #>

$ErrorActionPreference = "Stop"
if ($env:MVNW_VERBOSE -eq "true") {
  $VerbosePreference = "Continue"
}

# calculate distributionUrl, requires .mvn/wrapper/maven-wrapper.properties
$distributionUrl = (Get-Content -Raw "$scriptDir/.mvn/wrapper/maven-wrapper.properties" | ConvertFrom-StringData).distributionUrl
if (!$distributionUrl) {
  Write-Error "cannot read distributionUrl property in $scriptDir/.mvn/wrapper/maven-wrapper.properties"
}

switch -wildcard -casesensitive ( $($distributionUrl -replace '^.*/','') ) {
  "maven-mvnd-*" {
    $USE_MVND = $true
    $distributionUrl = $distributionUrl -replace '-bin\.[^.]*$',"-windows-amd64.zip"
    $MVN_CMD = "mvnd.cmd"
    break
  }
  default {
    $USE_MVND = $false
    $MVN_CMD = $script -replace '^mvnw','mvn'
    break
  }
}

# apply MVNW_REPOURL and calculate MAVEN_HOME
# maven home pattern: ~/.m2/wrapper/dists/{apache-maven-<version>,maven-mvnd-<version>-<platform>}/<hash>
if ($env:MVNW_REPOURL) {
  $MVNW_REPO_PATTERN = if ($USE_MVND) { "/org/apache/maven/" } else { "/maven/mvnd/" }
  $distributionUrl = "$env:MVNW_REPOURL$MVNW_REPO_PATTERN$($distributionUrl -replace '^.*'+$MVNW_REPO_PATTERN,'')"
}
$distributionUrlName = $distributionUrl -replace '^.*/',''
$distributionUrlNameMain = $distributionUrlName -replace '\.[^.]*$','' -replace '-bin$',''
$MAVEN_HOME_PARENT = "$HOME/.m2/wrapper/dists/$distributionUrlNameMain"
if ($env:MAVEN_USER_HOME) {
  $MAVEN_HOME_PARENT = "$env:MAVEN_USER_HOME/wrapper/dists/$distributionUrlNameMain"
}
$MAVEN_HOME_NAME = ([System.Security.Cryptography.MD5]::Create().ComputeHash([byte[]][char[]]$distributionUrl) | ForEach-Object {$_.ToString("x2")}) -join ''
$MAVEN_HOME = "$MAVEN_HOME_PARENT/$MAVEN_HOME_NAME"

if (Test-Path -Path "$MAVEN_HOME" -PathType Container) {
  Write-Verbose "found existing MAVEN_HOME at $MAVEN_HOME"
  Write-Output "MVN_CMD=$MAVEN_HOME/bin/$MVN_CMD"
  exit $?
}

if (! $distributionUrlNameMain -or ($distributionUrlName -eq $distributionUrlNameMain)) {
  Write-Error "distributionUrl is not valid, must end with *-bin.zip, but found $distributionUrl"
}

# prepare tmp dir
$TMP_DOWNLOAD_DIR_HOLDER = New-TemporaryFile
$TMP_DOWNLOAD_DIR = New-Item -Itemtype Directory -Path "$TMP_DOWNLOAD_DIR_HOLDER.dir"
$TMP_DOWNLOAD_DIR_HOLDER.Delete() | Out-Null
trap {
  if ($TMP_DOWNLOAD_DIR.Exists) {
    try { Remove-Item $TMP_DOWNLOAD_DIR -Recurse -Force | Out-Null }
    catch { Write-Warning "Cannot remove $TMP_DOWNLOAD_DIR" }
  }
}

New-Item -Itemtype Directory -Path "$MAVEN_HOME_PARENT" -Force | Out-Null

# Download and Install Apache Maven
Write-Verbose "Couldn't find MAVEN_HOME, downloading and installing it ..."
Write-Verbose "Downloading from: $distributionUrl"
Write-Verbose "Downloading to: $TMP_DOWNLOAD_DIR/$distributionUrlName"

$webclient = New-Object System.Net.WebClient
if ($env:MVNW_USERNAME -and $env:MVNW_PASSWORD) {
  $webclient.Credentials = New-Object System.Net.NetworkCredential($env:MVNW_USERNAME, $env:MVNW_PASSWORD)
}
[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
$webclient.DownloadFile($distributionUrl, "$TMP_DOWNLOAD_DIR/$distributionUrlName") | Out-Null

# If specified, validate the SHA-256 sum of the Maven distribution zip file
$distributionSha256Sum = (Get-Content -Raw "$scriptDir/.mvn/wrapper/maven-wrapper.properties" | ConvertFrom-StringData).distributionSha256Sum
if ($distributionSha256Sum) {
  if ($USE_MVND) {
    Write-Error "Checksum validation is not supported for maven-mvnd. `nPlease disable validation by removing 'distributionSha256Sum' from your maven-wrapper.properties."
  }
  Import-Module $PSHOME\Modules\Microsoft.PowerShell.Utility -Function Get-FileHash
  if ((Get-FileHash "$TMP_DOWNLOAD_DIR/$distributionUrlName" -Algorithm SHA256).Hash.ToLower() -ne $distributionSha256Sum) {
    Write-Error "Error: Failed to validate Maven distribution SHA-256, your Maven distribution might be compromised. If you updated your Maven version, you need to update the specified distributionSha256Sum property."
  }
}

# unzip and move
Expand-Archive "$TMP_DOWNLOAD_DIR/$distributionUrlName" -DestinationPath "$TMP_DOWNLOAD_DIR" | Out-Null
Rename-Item -Path "$TMP_DOWNLOAD_DIR/$distributionUrlNameMain" -NewName $MAVEN_HOME_NAME | Out-Null
try {
  Move-Item -Path "$TMP_DOWNLOAD_DIR/$MAVEN_HOME_NAME" -Destination $MAVEN_HOME_PARENT | Out-Null
} catch {
  if (! (Test-Path -Path "$MAVEN_HOME" -PathType Container)) {
    Write-Error "fail to move MAVEN_HOME"
  }
} finally {
  try { Remove-Item $TMP_DOWNLOAD_DIR -Recurse -Force | Out-Null }
  catch { Write-Warning "Cannot remove $TMP_DOWNLOAD_DIR" }
}

Write-Output "MVN_CMD=$MAVEN_HOME/bin/$MVN_CMD"

```

---

## Caminho: `pom.xml`

``` xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>3.4.4</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>br.com.cadastrocarros</groupId>
	<artifactId>cadastro-carros</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>cadastro-carros</name>
	<description>Projeto de Cadastro de Carros com Spring Boot, JPA e H2</description>
	<url/>
	<licenses>
		<license/>
	</licenses>
	<developers>
		<developer/>
	</developers>
	<scm>
		<connection/>
		<developerConnection/>
		<tag/>
		<url/>
	</scm>
	<properties>
		<java.version>21</java.version>
	</properties>
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>com.h2database</groupId>
			<artifactId>h2</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>

```

---

## Caminho: `README.md`

``` markdown
# Cadastro de Carros API

![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen)
![Java](https://img.shields.io/badge/Java-17%2B-blue)
![Maven](https://img.shields.io/badge/Maven-4.0.0-orange)
![Database](https://img.shields.io/badge/Database-H2_In_Memory-red)

## 📝 Descrição

Este projeto é uma API REST simples para o cadastro e gerenciamento de carros, desenvolvida com Spring Boot. Ele demonstra conceitos de CRUD (Create, Read, Update, Delete) utilizando Spring Web, Spring Data JPA (com Hibernate como provedor) e um banco de dados H2 em memória.

## ✨ Funcionalidades

* Criar novos registros de carros (marca, modelo, ano).
* Listar todos os carros cadastrados.
* Buscar um carro específico pelo seu ID.
* Atualizar os dados de um carro existente.
* Deletar um carro pelo seu ID.
* Buscar carros por marca.
* Console do banco de dados H2 acessível via navegador para consulta e inspeção.

## 🚀 Tecnologias Utilizadas

* **Java 17+:** Linguagem de programação principal.
* **Spring Boot 3.x:** Framework para criação rápida de aplicações Spring.
* **Spring Web:** Para construção de APIs RESTful.
* **Spring Data JPA:** Para facilitar o acesso a dados e persistência.
* **Hibernate:** Implementação JPA utilizada por baixo dos panos pelo Spring Data JPA.
* **H2 Database:** Banco de dados relacional em memória, ideal para desenvolvimento e testes.
* **Maven:** Ferramenta para gerenciamento de dependências e build do projeto.

## ✅ Pré-requisitos

Antes de começar, certifique-se de ter instalado em sua máquina:

* **JDK 17 ou superior:** [OpenJDK](https://openjdk.java.net/) ou outra distribuição.
* **Maven 3.6+:** [Apache Maven](https://maven.apache.org/download.cgi) (Opcional, pois o projeto inclui o Maven Wrapper).
* **Git:** Para clonar o repositório. [Git SCM](https://git-scm.com/).
* Uma ferramenta para testar APIs (Opcional, mas recomendado): Postman, Insomnia, ou `curl`.

## ⚙️ Como Configurar e Executar

1.  **Clone o repositório:**
    ```bash
    git clone <URL_DO_SEU_REPOSITORIO_GITHUB>
    cd cadastro-carros
    ```
    *(Substitua `<URL_DO_SEU_REPOSITORIO_GITHUB>` pela URL real do seu projeto no GitHub)*

2.  **Execute a aplicação usando o Maven Wrapper:**

    * No Linux ou macOS:
        ```bash
        ./mvnw spring-boot:run
        ```
    * No Windows (Prompt de Comando ou PowerShell):
        ```bash
        ./mvnw.cmd spring-boot:run
        ```

3.  **Alternativa (usando IDE):** Importe o projeto como um projeto Maven em sua IDE (IntelliJ IDEA, Eclipse, VS Code) e execute a classe principal `br.com.cadastrocarros.CadastroCarrosApplication`.

A aplicação estará rodando em `http://localhost:8087` (a porta foi alterada de 8080 para 8087 no `application.properties`).

## 🗄️ Acessando o Banco de Dados H2

Durante a execução da aplicação, você pode acessar o console web do banco de dados H2 para visualizar as tabelas, dados e executar consultas SQL diretamente.

1.  Abra seu navegador e acesse: `http://localhost:8087/h2-console`
2.  Na tela de login, utilize as seguintes informações (conforme configurado em `application.properties`):
    * **Driver Class:** `org.h2.Driver`
    * **JDBC URL:** `jdbc:h2:mem:carrodb`
    * **User Name:** `sa`
    * **Password:** (deixe em branco)
3.  Clique em "Connect".

## 📡 Endpoints da API

A API base está disponível em `http://localhost:8087/api/carros`.

---

### 1. Criar Carro

* **Método:** `POST`
* **URL:** `/api/carros`
* **Corpo da Requisição (JSON):**
    ```json
    {
        "marca": "Marca Exemplo",
        "modelo": "Modelo Exemplo",
        "ano": 2024
    }
    ```
* **Resposta (Sucesso):** `201 Created` com o objeto do carro criado no corpo.
* **Resposta (Erro):** `500 Internal Server Error` em caso de falha.

---

### 2. Listar Todos os Carros

* **Método:** `GET`
* **URL:** `/api/carros`
* **Resposta (Sucesso):** `200 OK` com uma lista JSON de carros. Retorna `204 No Content` se não houver carros.

---

### 3. Buscar Carro por ID

* **Método:** `GET`
* **URL:** `/api/carros/{id}`
    * Exemplo: `/api/carros/1`
* **Parâmetro de URL:** `{id}` - ID do carro a ser buscado.
* **Resposta (Sucesso):** `200 OK` com o objeto do carro encontrado.
* **Resposta (Erro):** `404 Not Found` se o ID não existir.

---

### 4. Atualizar Carro por ID

* **Método:** `PUT`
* **URL:** `/api/carros/{id}`
    * Exemplo: `/api/carros/1`
* **Parâmetro de URL:** `{id}` - ID do carro a ser atualizado.
* **Corpo da Requisição (JSON):** Objeto JSON com os *novos* dados do carro.
    ```json
    {
        "marca": "Marca Atualizada",
        "modelo": "Modelo Atualizado",
        "ano": 2025
    }
    ```
* **Resposta (Sucesso):** `200 OK` com o objeto do carro atualizado.
* **Resposta (Erro):** `404 Not Found` se o ID não existir.

---

### 5. Deletar Carro por ID

* **Método:** `DELETE`
* **URL:** `/api/carros/{id}`
    * Exemplo: `/api/carros/1`
* **Parâmetro de URL:** `{id}` - ID do carro a ser deletado.
* **Resposta (Sucesso):** `204 No Content` (sem corpo na resposta).
* **Resposta (Erro):** `404 Not Found` se o ID não existir, `500 Internal Server Error` em caso de outra falha.

---

### 6. Buscar Carros por Marca

* **Método:** `GET`
* **URL:** `/api/carros/marca/{marca}`
    * Exemplo: `/api/carros/marca/Toyota`
* **Parâmetro de URL:** `{marca}` - Nome da marca a ser buscada.
* **Resposta (Sucesso):** `200 OK` com uma lista JSON de carros da marca especificada. Retorna `204 No Content` se nenhum carro for encontrado para a marca.

---

## 🔧 Configuração

As principais configurações da aplicação podem ser encontradas no arquivo `src/main/resources/application.properties`, incluindo:

* `server.port`: Porta em que a aplicação roda (definida como 8087).
* `spring.datasource.*`: Configurações de conexão com o banco de dados H2.
* `spring.h2.console.*`: Configurações para habilitar e definir o caminho do console H2.
* `spring.jpa.*`: Configurações do Spring Data JPA e Hibernate (dialeto, DDL auto, mostrar SQL).

## 🏗️ Estrutura do Projeto (Simplificada)

```text
cadastro-carros/
├── .mvn/
│   └── wrapper/
│       └── maven-wrapper.properties
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── br/com/cadastrocarros/
│   │   │       ├── CadastroCarrosApplication.java  # Classe principal
│   │   │       ├── model/                          # Entidades JPA (Ex: Carro.java)
│   │   │       ├── repository/                     # Interfaces Spring Data JPA (Ex: CarroRepository.java)
│   │   │       └── controller/                     # Controladores REST (Ex: CarroController.java)
│   │   └── resources/
│   │       ├── application.properties        # Configurações da aplicação
│   │       ├── static/                       # Arquivos estáticos (CSS, JS, Imagens)
│   │       └── templates/                    # Templates HTML (se usar Thymeleaf/outros)
│   └── test/
│       └── java/
│           └── br/com/cadastrocarros/
│               └── CadastroCarrosApplicationTests.java # Testes
├── .gitattributes
├── .gitignore
├── HELP.md
├── mvnw
├── mvnw.cmd
└── pom.xml                                   # Arquivo de configuração do Maven


```

---

## Caminho: `.mvn\wrapper\maven-wrapper.properties`

``` properties
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#   http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.
wrapperVersion=3.3.2
distributionType=only-script
distributionUrl=https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.9/apache-maven-3.9.9-bin.zip

```

---

## Caminho: `src\main\java\br\com\cadastrocarros\CadastroCarrosApplication.java`

``` java
package br.com.cadastrocarros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CadastroCarrosApplication {

	public static void main(String[] args) {
		SpringApplication.run(CadastroCarrosApplication.class, args);
	}

}

```

---

## Caminho: `src\main\java\br\com\cadastrocarros\controller\CarroController.java`

``` java
package br.com.cadastrocarros.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cadastrocarros.model.Carro;
import br.com.cadastrocarros.repository.CarroRepository;

@RestController
@RequestMapping("/api/carros")

public class CarroController {
    private final CarroRepository carroRepository;

    // Constructor
    @Autowired
    public CarroController(CarroRepository carroRepository) {
        this.carroRepository = carroRepository;
    }

    // Post
    @PostMapping
    public ResponseEntity<Carro> criarCarro(@RequestBody Carro carro) {
        try {
            Carro novoCarro = carroRepository.save(carro);
            return new ResponseEntity<>(novoCarro, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get list all cars
    @GetMapping
    public ResponseEntity<List<Carro>> listarCarros() {
        try {
            List<Carro> carros = carroRepository.findAll();
            if (carros.isEmpty()) {
                return new ResponseEntity<>(carros, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(carros, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<Carro> obterCarroPorId(@PathVariable Long id) {
        Optional<Carro> carroOptional = carroRepository.findById(id);
        return carroOptional
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Get by marca
    @GetMapping("/marca/{marca}")
    public ResponseEntity<List<Carro>> obterCarrosPorMarca(@PathVariable String marca) {
        List<Carro> carros = carroRepository.findByMarca(marca);
        if (carros.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(carros, HttpStatus.OK);
    }

    // Put
    @PutMapping("/{id}")
    public ResponseEntity<Carro> atualizarCarro(@PathVariable Long id, @RequestBody Carro carro) {
        Optional<Carro> carroExistenteOptional = carroRepository.findById(id);

        if (carroExistenteOptional.isPresent()) {
            Carro carroExistente = carroExistenteOptional.get();
            carroExistente.setMarca(carro.getMarca());
            carroExistente.setModelo(carro.getModelo());
            carroExistente.setAno(carro.getAno());

            Carro carroSalvo = carroRepository.save(carroExistente);
            return new ResponseEntity<>(carroSalvo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCarro(@PathVariable Long id) {
        try {
            if (carroRepository.existsById(id)) {
                carroRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


```

---

## Caminho: `src\main\java\br\com\cadastrocarros\model\Carro.java`

``` java
package br.com.cadastrocarros.model;

import java.util.Objects;

// Import the necessary annotations
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Define the entity
@Entity
// Define the table name
@Table(name = "carros")

// Define the class
public class Carro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Define a estratégia de geração de chave primária
    private Long id;
    private String marca;
    private String modelo;
    private int ano;

    public Carro() {
        // Default constructor
    }

    public Carro(String marca, String modelo, int ano) {
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }
    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public int getAno() {
        return ano;
    }
    public void setAno(int ano) {
        this.ano = ano;
    }
    // Override equals and hashCode methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carro carro = (Carro) o;
        return Objects.equals(id, carro.id); // Compare by ID para a entidade Carro
    }
    // Override para gerar apenas o hash code do ID
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    // Formar o return do Carro com os dados
    @Override
    public String toString() {
        return "Carro{" +
                "id=" + id +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", ano=" + ano +
                '}';
    }
}

```

---

## Caminho: `src\main\java\br\com\cadastrocarros\repository\CarroRepository.java`

``` java
package br.com.cadastrocarros.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cadastrocarros.model.Carro;

@Repository
// Define a interface que estende JpaRepository para a entidade Carro
public interface CarroRepository extends JpaRepository<Carro, Long> {
    List<Carro> findByMarca(String marca);
    List<Carro> findByAnoGreaterThan(int ano);
}

```

---

## Caminho: `src\main\resources\application.properties`

``` properties
spring.application.name=cadastro-carros
# --- Configuração do DataSource (H2) ---
# URL de conexão para um banco em memória chamado 'carrodb'
spring.datasource.url=jdbc:h2:mem:carrodb
# Driver JDBC do H2
spring.datasource.driverClassName=org.h2.Driver
# Usuário padrão do H2
spring.datasource.username=sa
# Senha padrão do H2 (vazia)
spring.datasource.password=

# --- Configuração do Console H2 ---
# Habilita o console web do H2
spring.h2.console.enabled=true
# Caminho para acessar o console (ex: http://localhost:8080/h2-console)
# Deixe o padrão ou defina um, como /h2-console
spring.h2.console.path=/h2-console

# --- Configuração do JPA/Hibernate ---
# Dialeto específico para o H2 (ajuda o Hibernate a gerar SQL compatível)
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

# Estratégia de Geração do Schema DDL (Data Definition Language)
# 'update': Cria/atualiza o schema baseado nas entidades ao iniciar. Bom para desenvolvimento.
# Outras opções: 'create' (recria sempre), 'create-drop' (cria e apaga ao final), 'validate' (valida), 'none' (não faz nada)
spring.jpa.hibernate.ddl-auto=update

# Mostra o SQL gerado pelo Hibernate no console
spring.jpa.show-sql=true

# Formata o SQL exibido para melhor legibilidade
spring.jpa.properties.hibernate.format_sql=true

# Porta em que a aplicação será iniciada (padrão: 8087)
server.port=8087
```

---

## Caminho: `src\test\java\br\com\cadastrocarros\CadastroCarrosApplicationTests.java`

``` java
package br.com.cadastrocarros;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CadastroCarrosApplicationTests {

	@Test
	void contextLoads() {
	}

}

```
