# ===============================================
# OUTPUTS
# ===============================================

output "namespace" {
  description = "Namespace do stock service"
  value       = kubernetes_namespace.stock.metadata[0].name
}

output "service_name" {
  description = "Nome do service Kubernetes"
  value       = kubernetes_service.stock_service.metadata[0].name
}

output "loadbalancer_hostname" {
  description = "Hostname do Load Balancer"
  value       = kubernetes_service.stock_service.status[0].load_balancer[0].ingress[0].hostname
}

output "deployment_name" {
  description = "Nome do deployment"
  value       = kubernetes_deployment.stock_service.metadata[0].name
}

output "hpa_name" {
  description = "Nome do HPA"
  value       = kubernetes_horizontal_pod_autoscaler_v2.stock_service.metadata[0].name
}

# SQS Queue URLs
output "sqs_stock_requested_url" {
  description = "URL da fila work-order-stock-requested"
  value       = aws_sqs_queue.stock_requested.url
}

output "sqs_stock_approved_url" {
  description = "URL da fila work-order-stock-approved"
  value       = aws_sqs_queue.stock_approved.url
}

output "sqs_stock_cancel_requested_url" {
  description = "URL da fila work-order-stock-cancel-requested"
  value       = aws_sqs_queue.stock_cancel_requested.url
}

output "sqs_stock_reserved_url" {
  description = "URL da fila stock-reserved-queue"
  value       = aws_sqs_queue.stock_reserved.url
}

output "sqs_stock_failed_url" {
  description = "URL da fila stock-failed-queue"
  value       = aws_sqs_queue.stock_failed.url
}

# SQS Queue ARNs
output "sqs_stock_requested_arn" {
  description = "ARN da fila work-order-stock-requested"
  value       = aws_sqs_queue.stock_requested.arn
}

output "sqs_stock_approved_arn" {
  description = "ARN da fila work-order-stock-approved"
  value       = aws_sqs_queue.stock_approved.arn
}

output "sqs_stock_cancel_requested_arn" {
  description = "ARN da fila work-order-stock-cancel-requested"
  value       = aws_sqs_queue.stock_cancel_requested.arn
}

output "sqs_stock_reserved_arn" {
  description = "ARN da fila stock-reserved-queue"
  value       = aws_sqs_queue.stock_reserved.arn
}

output "sqs_stock_failed_arn" {
  description = "ARN da fila stock-failed-queue"
  value       = aws_sqs_queue.stock_failed.arn
}

output "stock_lb_name" {
  description = "Nome do Load Balancer do Stock Service"
  value       = "${var.service}-service-lb-${var.environment}"
}
