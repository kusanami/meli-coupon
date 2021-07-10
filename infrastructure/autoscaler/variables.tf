variable "region" {
  description = "the AWS region in which resources are created"
}

variable "name" {
  description = "the name of your stack"
}

variable "environment" {
  description = "the name of your environment"
}

variable "config_path" {
  description = "path to a kubernetes config file"
}

variable "cidr" {
  description = "The CIDR block for the VPC."
}

variable "private_subnets" {
  description = "a list of CIDRs for private subnets in your VPC"
}

variable "public_subnets" {
  description = "a list of CIDRs for public subnets in your VPC"
}

variable "availability_zones" {
  description = "a comma-separated list of availability zones"
}

variable "map_accounts" {
  description = "Additional AWS account numbers to add to the aws-auth configmap"
}

variable "map_roles" {
  description = "Additional IAM roles to add to the aws-auth configmap"
}

variable "map_users" {
  description = "Additional IAM users to add to the aws-auth configmap"
}

variable "cluster_id" {
  description = "cluster id to eks cluster"
}

variable "worker_iam_role" {
  description = "Worker IAM role"
}
