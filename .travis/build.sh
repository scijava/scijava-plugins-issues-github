#!/bin/sh
curl -fsLO https://raw.githubusercontent.com/scijava/scijava-scripts/master/travis-build.sh
sh travis-build.sh $encrypted_80c4a7f8fe67_key $encrypted_80c4a7f8fe67_iv
