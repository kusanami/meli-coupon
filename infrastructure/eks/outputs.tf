output "config_path" {
  description = "Path to new kubectl config file"
  value       = pathexpand("${var.kubeconfig_path}/config")
}

output "worker_iam_role" {
  value   = aws_iam_role.eks_node_group_role
}

output "cluster_id" {
  description = "ID of the created cluster"
  value       = aws_eks_cluster.main.id
}

output "cluster_issuer" {
  description = "ID of the created cluster"
  value       = data.aws_eks_cluster.cluster.identity[0].oidc[0].issuer
}
