variable "name" {
  description = "the name of your stack"
}

variable "environment" {
  description = "the name of your environment"
}

variable "region" {
  description = "the AWS region in which resources are created"
}

variable "vpc_id" {
  description = "The VPC the cluster should be use from vpc module"
}

variable "cluster_id" {
  description = "The ID of the cluster where the ingress controller should be attached"
}

variable "config_path" {
  description = "Path where the config file for kubectl should be written to"
}

variable "knative_version" {
  description = "The version to install knative"
}

variable "domain" {
  description = "The domain used for the application service"
}

variable "worker_iam_role" {
  description = "Worker IAM role"
}
