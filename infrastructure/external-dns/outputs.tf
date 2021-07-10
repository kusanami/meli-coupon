output "aws_iam_policy_arn" {
  value = length(aws_iam_policy.external_dns) == 0 ? null : aws_iam_policy.external_dns.arn
}
