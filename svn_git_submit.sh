#!/usr/bin/env bash

git add .
git commit -m "$1"
git push

# start svn commit

echo "Start svn commit "
cd automation-common/src
svn commit -m "$1"

cd ../../
# commit docs
echo "add docs into svn"
cd docs/automation-docs/
svn commit -m "$1"

echo "END OF PUSHING"

