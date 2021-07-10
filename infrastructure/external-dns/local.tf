locals {
  kubernetes_resources_labels = merge({
    "app" = "external-dns",
  }, var.kubernetes_resources_labels)

  kubernetes_deployment_labels_selector = {
    "app" = "external-dns",
  }

  kubernetes_deployment_labels = merge(local.kubernetes_deployment_labels_selector, local.kubernetes_resources_labels)

  kubernetes_deployment_image = "${var.kubernetes_deployment_image_registry}:${var.kubernetes_deployment_image_tag}"

  kubernetes_deployment_container_args_base = [
    "--source=service",
    "--source=ingress",
    "--provider=aws",
    "--aws-zone-type=public",
    "--registry=txt",
    "--txt-owner-id=${var.owner_id}",
  ]

  kubernetes_deployment_container_args = concat(
    local.kubernetes_deployment_container_args_base
  )
}
