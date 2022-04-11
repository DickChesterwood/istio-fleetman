#!/usr/bin/env bash

mkIPadd="$(minikube ip)"

function pMsg() {
    theMessage="$1"
    printf '%s\n' "$theMessage"
}



###---
### Display the initial web resources
###---
set +x
pMsg "Kiali:  http://$mkIPadd:31000"
pMsg "Jaeger: http://$mkIPadd:31001"
pMsg "Apps:   http://$mkIPadd:30080"

