# git-push.ps1

Write-Host "========== Start Git Operation Script ==========" -ForegroundColor Cyan

function Run-GitCommand {
    param (
        [string]$Command,
        [string]$ErrorMessage
    )

    Write-Host ">>> $Command" -ForegroundColor Yellow
    Invoke-Expression $Command

    if ($LASTEXITCODE -ne 0) {
        Write-Host "❌ $ErrorMessage" -ForegroundColor Red
        exit $LASTEXITCODE
    }
}

Run-GitCommand "git stash" "git stash failed"

Run-GitCommand "git push sevenme-server-pulian develop" "Push develop failed"

Run-GitCommand "git checkout test" "Checkout test failed"

Run-GitCommand "git merge develop" "Merge develop into test failed"

Run-GitCommand "git push sevenme-server-pulian test" "Push test failed"

Run-GitCommand "git checkout develop" "Checkout develop failed"

Run-GitCommand "git stash pop" "git stash pop failed"

Write-Host "========== Git Operation Script Completed ==========" -ForegroundColor Green
pause
