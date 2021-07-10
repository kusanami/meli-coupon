variable "name" {
  description = "the name of your stack"
  default     = "coupon"
}

variable "environment" {
  description = "the name of your environment, e.g."
  default     = "challenge"
}

variable "region" {
  description = "the AWS region in which resources are created"
  default     = "us-east-1"
}

variable "instances_desired_size" {
  description = "Desired number of instances in the eks cluster"
  default     = 2
}

variable "instances_max_size" {
  description = "Maximum number of instances in the eks cluster"
  default     = 4
}

variable "instances_min_size" {
  description = "Minimum number of instances in the eks cluster"
  default     = 2
}

variable "instance_type" {
  description = "Type of instance to use in the eks cluster"
  default     = "t3.medium"
}

variable "availability_zones" {
  description = "a comma-separated list of availability zones"
  default     = ["us-east-1a", "us-east-1b", "us-east-1c"]
}

variable "cidr" {
  description = "The CIDR block for the VPC."
  default     = "10.0.0.0/16"
}

variable "private_subnets" {
  description = "a list of CIDRs for private subnets in your VPC"
  default     = ["10.0.0.0/20", "10.0.32.0/20", "10.0.64.0/20"]
}

variable "public_subnets" {
  description = "a list of CIDRs for public subnets in your VPC"
  default     = ["10.0.16.0/20", "10.0.48.0/20", "10.0.80.0/20"]
}

variable "k8s_version" {
  description = "kubernetes version to use in the EKS cluster"
  default     = "1.19"
}

variable "knative_version" {
  description  = "Knative version to use for manage the autoscaler"
  default       = "v0.20.0"
}

variable "domain" {
  description = "The domain used for the application service"
  default     = "couponlm.net"
}

# Secret variables
variable "kubeconfig_path" {
  description = "path to a kubernetes config file"
  type        = string
}

variable "map_accounts" {
  description = "Additional AWS account numbers to add to the aws-auth configmap."
  type        = list(string)
}

variable "map_roles" {
  description = "Additional IAM roles to add to the aws-auth configmap."
  type = list(object({
    rolearn  = string
    username = string
    groups   = list(string)
  }))
}

variable "map_users" {
  description = "Additional IAM users to add to the aws-auth configmap."
  type = list(object({
    userarn  = string
    username = string
    groups   = list(string)
  }))
}
