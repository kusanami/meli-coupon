#!/bin/bash

#Knative serving installation
kubectl apply --kubeconfig ${kubeconfig} --filename https://github.com/jetstack/cert-manager/releases/download/v1.4.0/cert-manager.yaml
sleep 5

kubectl apply --kubeconfig ${kubeconfig} --filename https://raw.githubusercontent.com/kubernetes-sigs/aws-load-balancer-controller/main/docs/install/v2_1_3_full.yaml
sleep 10

kubectl apply --kubeconfig ${kubeconfig} --filename https://github.com/knative/serving/releases/download/${knative_version}/serving-crds.yaml
sleep 5

kubectl apply --kubeconfig ${kubeconfig} --filename https://github.com/knative/serving/releases/download/${knative_version}/serving-core.yaml
sleep 30

#Patch alb-controller
kubectl patch --kubeconfig ${kubeconfig} deployment aws-load-balancer-controller --namespace kube-system \
  --type='json' \
  -p='[{"op": "replace", "path": "/spec/template/spec/containers/0/args", "value": [
  "--cluster-name='"${cluster_name}-${environment}"'",
  "--ingress-class=alb",
  "--aws-vpc-id='"${vpc_id}"'",
  "--aws-region='"${aws_region}"'"
  ]}]'

kubectl patch --kubeconfig ${kubeconfig} configmap/config-autoscaler \
  --namespace knative-serving \
  --type merge \
  --patch '{"data":{"scale-to-zero-pod-retention-period":"500s"}}'

kubectl apply --kubeconfig ${kubeconfig} --filename https://github.com/knative/net-kourier/releases/download/${knative_version}/kourier.yaml
sleep 25

kubectl patch --kubeconfig ${kubeconfig} configmap/config-network \
  --namespace knative-serving \
  --type merge \
  --patch '{"data":{"ingress.class":"kourier.ingress.networking.knative.dev"}}'

kubectl patch --kubeconfig ${kubeconfig} configmap/config-network \
  --namespace knative-serving \
  --type merge \
  --patch '{"data":{"domainTemplate":"{{.Name}}.{{.Domain}}"}}'

kubectl patch --kubeconfig ${kubeconfig} configmap/config-domain \
  --namespace knative-serving \
  --type merge \
  --patch '{"data":{"'"${domain}"'":""}}'


#Pod reset for knative-serving
kubectl delete --kubeconfig ${kubeconfig} pods --all --namespace kube-system
sleep 15
kubectl delete --kubeconfig ${kubeconfig} pods --all --namespace knative-serving
sleep 10
kubectl delete --kubeconfig ${kubeconfig} pods --all --namespace kourier-system
sleep 15
