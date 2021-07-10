resource "null_resource" "coupon-service" {

  provisioner "local-exec" {
    command = "kubectl apply --kubeconfig ${var.config_path} --filename ${path.module}/data/coupon_service.yaml"
  }

}

resource "time_sleep" "wait_45_seconds" {
  create_duration = "45s"
}

resource "null_resource" "coupon-ingress2" {

  provisioner "local-exec" {
    command = "kubectl apply --kubeconfig ${var.config_path} --filename ${path.module}/data/coupon_ingress.yaml"
  }

  depends_on = [time_sleep.wait_45_seconds]

}
