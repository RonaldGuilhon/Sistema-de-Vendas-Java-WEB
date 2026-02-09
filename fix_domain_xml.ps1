$domainXmlPath = "c:\glassfish5\glassfish\domains\domain1\config\domain.xml"

if (-Not (Test-Path $domainXmlPath)) {
    Write-Error "Arquivo domain.xml não encontrado em: $domainXmlPath"
    exit 1
}

$content = Get-Content $domainXmlPath -Raw

# Correção 1: Adicionar aspas em java.ext.dirs para lidar com espaços no caminho do Java (Program Files)
# Procura por: <jvm-options>-Djava.ext.dirs=...anything...</jvm-options>
# Substitui por: <jvm-options>-Djava.ext.dirs="...anything..."</jvm-options>
$patternExtDirs = '(<jvm-options>-Djava\.ext\.dirs=)(.*?)(</jvm-options>)'
if ($content -match $patternExtDirs) {
    # Verifica se já tem aspas para não duplicar
    if ($matches[2] -notmatch '^".*"$') {
        Write-Host "Aplicando correção de aspas em java.ext.dirs..."
        $content = $content -replace $patternExtDirs, '$1"$2"$3'
    } else {
        Write-Host "java.ext.dirs já possui aspas."
    }
}

# Correção 2: Adicionar aspas em outras propriedades que podem conter caminhos com espaços
# Exemplo genérico para propriedades que começam com caminho de instalação
# Mas vamos focar no java.ext.dirs que é o culpado identificado

# Salva o arquivo corrigido
Set-Content -Path $domainXmlPath -Value $content -Encoding UTF8
Write-Host "Arquivo domain.xml corrigido com sucesso!"
Write-Host "Tentando iniciar o GlassFish..."

# Tenta iniciar
Start-Process -FilePath "c:\glassfish5\bin\asadmin.bat" -ArgumentList "start-domain --verbose" -NoNewWindow -Wait
