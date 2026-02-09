Stop-Process -Name "java" -Force -ErrorAction SilentlyContinue

Write-Host "Cleaning GlassFish cache..."
Remove-Item -Path "c:\glassfish5\glassfish\domains\domain1\osgi-cache" -Recurse -Force -ErrorAction SilentlyContinue
Remove-Item -Path "c:\glassfish5\glassfish\domains\domain1\generated" -Recurse -Force -ErrorAction SilentlyContinue

Write-Host "Starting GlassFish..."
Start-Process -FilePath "c:\glassfish5\bin\asadmin.bat" -ArgumentList "start-domain --verbose" -NoNewWindow -Wait
