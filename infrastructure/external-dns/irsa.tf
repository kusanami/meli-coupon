data "aws_caller_identity" "current" {}

resource "aws_iam_role" "external_dns_role" {
  name = "external-dns"

  assume_role_policy = jsonencode({
    "Version": "2012-10-17",
    "Statement": [
      {
        "Effect": "Allow",
        "Principal": {
          "Federated": format(
            "arn:aws:iam::${data.aws_caller_identity.current.account_id}:%s",
            replace(
              "${var.cluster_issuer}",
              "https://",
              "oidc-provider/"
            )
          )
        },
        "Action": "sts:AssumeRoleWithWebIdentity",
        "Condition": {
          "StringEquals": {
            format(
              "%s:sub",
              trimprefix(
                "${var.cluster_issuer}",
                "https://"
              )
            ) : "system:serviceaccount:kube-system:external-dns"
          }
        }
      }
    ]
  })
}

resource "aws_iam_policy" "external_dns" {

  name = "external-dns-policy"
  path = "/"
  description = "Allows access to resources needed to run external dns."

  policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "route53:ChangeResourceRecordSets"
      ],
      "Resource": [
        "arn:aws:route53:::hostedzone/*"
      ]
    },
    {
      "Effect": "Allow",
      "Action": [
        "route53:ListHostedZones",
        "route53:ListResourceRecordSets"
      ],
      "Resource": [
        "*"
      ]
    }
  ]
}
EOF
}

resource "aws_iam_role_policy_attachment" "external_dns" {
  role = aws_iam_role.external_dns_role.name
  policy_arn = aws_iam_policy.external_dns.arn
}
