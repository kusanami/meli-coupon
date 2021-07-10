variable "region" {
  description = "the AWS region in which resources are created"
}

variable "config_path" {
  description = "Path where the config file for kubectl should be written to"
}

variable "certificate_arn" {
  description = "somethung"
  default     = "arn:aws:acm:us-east-1:763172254126:certificate/f2822922-2eb6-4f48-a444-0b31c2b870ee"
}

variable "domain_service"{
  description = "some"
  default     = "coupon.default.couponlm.net"
}
