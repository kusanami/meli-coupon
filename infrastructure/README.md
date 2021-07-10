# EKS cluster installation

This repository contains the instructions and resources necessary to install knative in the EKS cluster for AWS.

The system pods will have their respective EC2 instances, however **fargate instances** will be used for applications created within the cluster. As proof of concept we want to validate the proper operation of the applications using **knative + fargate**.

The code is written in **terraform**, and it's necessary to have an AWS account and their respective credentials.

In order to be able to execute the following instructions, it's necessary to create a file secret.tfvars in this same directory (location of main.tf). This file **secret.tfvars** contains the private data of connection to AWS that will allow the creation of the cluster of EKS and the creation of the internal permissions for its proper operation.

The contents of the file **secret.tfvars** can be obtained by copying the sample file **secret.tfvars.txt** included here and replacing the values indicated as: $ {something}

## Requeriments

These are the necessary requirements to be able to run the terraform scripts and perform the knative installation:

 - Have a terraform version **>~0.14** installed : [Terraform](https://www.terraform.io)
 - Have credentials and account to AWS services
 - Have kubectl installed [kubectl](https://kubernetes.io/es/docs/tasks/tools/install-kubectl/)

## Authentication

Authentication to the kubernetes cluster is carried out through a configuration file generated at the time of execution of this project, it's not necessary to edit any file locally, however the **KUBECONFIG** variable must be exported according to the instructions below.

For more information about kubeconfig (k8s configuration file) see: [kubeconfig](https://kubernetes.io/docs/tasks/access-application-cluster/configure-access-multiple-clusters/)

## Tfstate

Only **tfstate local**, AWS tfstate module not used for this proof of concept.

## Before to start

- Make sure you have sufficient resources in your AWS account, access to elastic IP, load balancer, elastic network interfaces, budget, etc.

- Keep in mind that everything tied to your AWS account, being a proof of concept it is good that you start with a test account with sufficient permissions

## DEPLOY THE CODE:

These are the necessary steps to complete the creation of the EKS cluster in AWS and the installation of Knative, a **mock of credibanco** is also created automatically to complete the proof of concept.

```bash
cd pocs/fargate-eks
```

- First you must **create the secret.tfvars file** using the example file **secret.tfvars.txt as a template**, then you must replace the variables $ {whatever} with the respective **values of the AWS account** in the created file.

- Execute the **plan** for the project

```bash
echo yes | terraform plan -var-file="secret.tfvars"
```

- Execute the **apply** for the project

```bash
echo yes | terraform apply -var-file="secret.tfvars"
```

  Please be patient and wait for the project to finish running. Upon completion, a file called **kubeconfig_knative-eks** (kubeconfig + name of cluster) should be created.

- Success! , now create the following environment variable in order to interact with your cluster:

 ```bash
 export KUBECONFIG="${GIT_HOME}/knative-cluster/pocs/fargate-eks/kubeconfig_knative-eks" # Replace your git home for the path.
 ```

- To test the deployed mock with fargate instance, it's necessary to obtain the **AWS load balancer DNS** created for the knative ingress:

```bash
kubectl get ingress -n kourier-system knative-external-proxy -o jsonpath='{.status.loadBalancer.ingress[].hostname}'
```

The output should look something like:

```bash
75986dcb-kouriersystem-kna-dc22-15034488.us-west-2.elb.amazonaws.com
```

- The request to verify the operation of the mock (Replace with your AWS ALB):

```bash
cd $GIT_HOME/knative-cluster
```

```bash
curl -v -X POST -H "Content-Type: text/xml" \
     -H "Host: credibanco.mocks.payu.aws-qa" \
     --data @docs/samples/mocks/credibanco/authorization.xml http://75986dcb-kouriersystem-kna-dc22-15034488.us-west-2.elb.amazonaws.com/webservicevisa/autorizarcompra.asmx
```

## DESTROY THE CODE:

To destroy the cluster and other resources created, just execute:

 ```bash
 echo yes | terraform destroy -var-file="secret.tfvars"
 ```

 ### I have a problem, who should I contact?

 * Me ( Luis Miguel Ruiz - *luis.ruiz@payu.com* )
