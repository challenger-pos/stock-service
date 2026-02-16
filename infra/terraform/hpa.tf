# ===============================================
# HORIZONTAL POD AUTOSCALER (HPA)
# ===============================================

resource "kubernetes_horizontal_pod_autoscaler_v2" "stock_service" {
  metadata {
    name      = "${var.app_name}-hpa"
    namespace = kubernetes_namespace.stock.metadata[0].name

    labels = {
      app         = var.app_name
      service     = var.service
      environment = var.environment
    }
  }

  spec {
    min_replicas = var.hpa_min_replicas
    max_replicas = var.hpa_max_replicas

    scale_target_ref {
      api_version = "apps/v1"
      kind        = "Deployment"
      name        = kubernetes_deployment.stock_service.metadata[0].name
    }

    # CPU Utilization Metric
    metric {
      type = "Resource"
      resource {
        name = "cpu"
        target {
          type                = "Utilization"
          average_utilization = var.hpa_target_cpu_percentage
        }
      }
    }

    # Memory Utilization Metric
    metric {
      type = "Resource"
      resource {
        name = "memory"
        target {
          type                = "Utilization"
          average_utilization = var.hpa_target_memory_percentage
        }
      }
    }

    # Behavior - controla velocidade de scale up/down
    behavior {
      scale_down {
        stabilization_window_seconds = 300  # 5 min aguardando antes de scale down
        policy {
          type           = "Percent"
          value          = 50
          period_seconds = 60
        }
      }

      scale_up {
        stabilization_window_seconds = 0  # Scale up imediato
        policy {
          type           = "Percent"
          value          = 100
          period_seconds = 60
        }
      }
    }
  }
}
