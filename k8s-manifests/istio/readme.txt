

These files are just here as a temporary convenience, get them from helm or the istio download.

crd files are from <istio>/install/kubernetes/helm/istio-init/files/
main file is from istio install dir


after applying,

kubectl label namespace <namespace> istio-injection=enabled
