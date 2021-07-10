variable "name" {
  description = "the name of your stack"
}

variable "environment" {
  description = "the name of your environment"
}

variable "region" {
  description = "the AWS region in which resources are created"
}

variable "k8s_version" {
  description = "kubernetes version to use in the EKS cluster"
}

variable "vpc_id" {
  description = "The VPC the cluster should be use from vpc module"
}

variable "private_subnets" {
  description = "a list of CIDRs for private subnets in your VPC"
}

variable "public_subnets" {
  description = "a list of CIDRs for public subnets in your VPC"
}

variable "kubeconfig_path" {
  description = "Path where the config file for kubectl should be written to"
}

variable "instances_desired_size" {
  description = "Desired number of instances in the eks cluster"
}

variable "instances_max_size" {
  description = "Maximum number of instances in the eks cluster"
}

variable "instances_min_size" {
  description = "Minimum number of instances in the eks cluster"
}

variable "instance_type" {
  description = "Type of instance to use in the eks cluster"
}
