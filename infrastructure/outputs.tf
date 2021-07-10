output "config_path" {
  description = "Path to new kubectl config file"
  value       = module.eks.config_path
}

output "cluster_id" {
  description = "ID of the created cluster"
  value       = module.eks.cluster_id
}
