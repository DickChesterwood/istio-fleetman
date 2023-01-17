#!/usr/bin/env bash
#  PURPOSE: Installs and configures Istio on minikube
# -----------------------------------------------------------------------------
#  PREREQS: a) minikube, istioctl, kubectl and virtualbox
#           b) set some sensible minikube defaults:
#                * minikube config set cpus 2
#                * minikube config set memory 4096
#                * minikube config set driver hyperkit
#                * minikube config set WantVirtualBoxDriverWarning false
#           c)
# -----------------------------------------------------------------------------
#  EXECUTE:
# -----------------------------------------------------------------------------
#     TODO: 1)
#           2)
#           3)
# -----------------------------------------------------------------------------
#   AUTHOR: todd-dsm
# -----------------------------------------------------------------------------
#  CREATED: 2021/10/00
# -----------------------------------------------------------------------------
set -x


###----------------------------------------------------------------------------
### VARIABLES
###----------------------------------------------------------------------------
# ENV Stuff
#: "${1?  Wheres my first agument, bro!}"
KUBECONFIG="$HOME/.kube/config"
# Data
setupDir='_course_files/warmup-exercise'

###----------------------------------------------------------------------------
### FUNCTIONS
###----------------------------------------------------------------------------
function pMsg() {
    theMessage="$1"
    printf '%s\n' "$theMessage"
}


###----------------------------------------------------------------------------
### MAIN PROGRAM
###----------------------------------------------------------------------------
### Get minikube going
###   * Reset the space
###   * Start a new cluster
###---
minikube delete
cat /dev/null > "$KUBECONFIG"

minikube start
mkIPadd="$(minikube ip)"


###---
### Deploy Istio via manifests
###---
pMsg "Installing Istio CRDs out to the cluster..."
kubectl apply -f "$setupDir/1-istio-init.yaml"

pMsg "These are the new Istio CRDs..."
kubectl -n istio-system get crd


###---
### Install Istio and Supporting Services
###---
pMsg "Installingi Istio..."
kubectl apply -f "$setupDir/2-istio-minikube.yaml"
sleep 10s

pMsg "Waiting for Istio pods to go ready..."
kubectl -n istio-system wait --for=condition=ready pod -l app=jaeger --timeout=2m
kubectl -n istio-system wait --for=condition=ready pod -l app=istiod --timeout=2m
kubectl -n istio-system wait --for=condition=ready pod -l app=grafana --timeout=2m
kubectl -n istio-system wait --for=condition=ready pod -l app=prometheus --timeout=2m
kubectl -n istio-system wait --for=condition=ready pod -l app=istio-ingressgateway --timeout=2m
kubectl -n istio-system wait --for=condition=ready pod -l app=istio-egressgateway --timeout=2m
kubectl -n istio-system wait --for=condition=ready pod -l app=kiali --timeout=2m


###---
### Set a password for the Kiali dashboard; base64 encoded
###---
pMsg "Configuring the Kiali dashboard..."
kubectl apply -f "$setupDir/3-kiali-secret.yaml"

pMsg "The password is set:"
kubectl -n istio-system get secrets kiali


###---
### Label the namespace where apps will be deployed: default
###---
pMsg "Labeling the default namespace..."
kubectl label namespaces default istio-injection=enabled
kubectl describe namespaces default | grep -E '^(Name|Labels)'


###---
### Deploy the troubled apps
###---
kubectl apply -f "$setupDir/4-application-full-stack.yaml"

pMsg "Waiting for Apps pods to go ready..."
kubectl wait --for=condition=ready pod -l app=api-gateway --timeout=2m
kubectl wait --for=condition=ready pod -l app=staff-service --timeout=2m
kubectl wait --for=condition=ready pod -l app=position-tracker --timeout=2m
kubectl wait --for=condition=ready pod -l app=vehicle-telemetry --timeout=2m
kubectl wait --for=condition=ready pod -l app=webapp --timeout=2m
kubectl wait --for=condition=ready pod -l app=photo-service --timeout=2m
kubectl wait --for=condition=ready pod -l app=position-simulator --timeout=2m


###---
### REQ
###---


###---
### REQ
###---


###---
### REQ
###---


###---
### REQ
###---


###---
### REQ
###---


###---
### REQ
###---


###---
### REQ
###---


###---
### Display the initial web resources
###---
#set +x
#pMsg "Kiali:  http://$mkIPadd:31000"
#pMsg "Jaeger: http://$mkIPadd:31001"
#pMsg "Apps:   http://$mkIPadd:30080"


###---
### fin~
###---
exit 0
