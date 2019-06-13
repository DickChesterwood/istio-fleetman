# istio-fleetman

Run locally:

```
ng serve
```
The location of the API gateway is contained in environment.ts

To build for production - this uses environment.prod.ts

```
ng build --configuration=production
```

A sample project to demonstrate Istio's features. Based on the k8s-fleetman project, with the now unnecessary features such as Ribbon and Hystrix removed.

Supports the training course I'm developing for VirtualPairProgrammers.com, Udemy and maybe others, planned for release sometime in mid 2019.
