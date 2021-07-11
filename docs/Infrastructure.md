# EKS cluster installation

This repository contains the instructions and resources necessary to create the EKS cluster on AWS and other infrastructure resources.

The code is written in **terraform**, and it's necessary to have an AWS account and their respective credentials.

In order to be able to execute the following instructions, it's necessary to create a file secret.tfvars in this same directory (location of main.tf). This file **secret.tfvars** contains the private data of connection to AWS that will allow the creation of the cluster of EKS and the creation of the internal permissions for its proper operation.

The contents of the file **secret.tfvars** can be obtained by copying the sample file **secret.tfvars.txt** included here and replacing the values indicated as: $ {something}

## Requeriments

These are the necessary requirements to be able to run the terraform scripts and perform the cluster installation:

 - Have a terraform version **>=0.14** installed : [Terraform](https://www.terraform.io)
 - Have credentials and account to AWS services
 - Have kubectl installed [kubectl](https://kubernetes.io/es/docs/tasks/tools/install-kubectl/)

## Authentication

Authentication to the kubernetes cluster is carried out through a configuration file generated at the time of execution of this project, it's not necessary to edit any file locally, however the **KUBECONFIG** variable must be exported according to the instructions below.

For more information about kubeconfig (k8s configuration file) see: [kubeconfig](https://kubernetes.io/docs/tasks/access-application-cluster/configure-access-multiple-clusters/)

## Tfstate

This terraform module has **remote state set to S3**, which allows you to retrieve the status of the infrastructure from anywhere that has access to the AWS account.

## Before to start

- Make sure you have sufficient resources in your AWS account, EKS clusters, load balancer, IAM, budget, etc.

## Deploy the infrastucture

These are the necessary steps to complete the creation of the EKS cluster in AWS and the installation of the cluster:

```bash
cd ${git_home}/meli-coupon/infrastructure/
```

- First you must **create the secret.tfvars file** using the example file **secret.tfvars.txt as a template**, then you must replace the variables $ {whatever} with the respective **values of the AWS account** in the created file.

- Execute the indicated

```bash
terraform init
```

- Execute the **plan** for the project

```bash
echo yes | terraform plan -var-file="secret.tfvars"
```

- Execute the **apply** for the project

```bash
echo yes | terraform apply -var-file="secret.tfvars"
```

  Please be patient and wait for the project to finish running. Upon completion, a file called **config** (kubeconfig) should be created in the path specified (secret.tfvars).

- **Success!** , now all the necessary infrastructure to deploy our application is created!

## Outputs:

In order to continue the configuration and provisioning of the continuous integration tasks, the following outputs from the terraform code execution must be stored:
  - **config**
  - **Database Endpoint**

## Destroy infrastucture:

To destroy the cluster and other resources created, just execute:

 ```bash
 echo yes | terraform destroy -var-file="secret.tfvars"
 ```
