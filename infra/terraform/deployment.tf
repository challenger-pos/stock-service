# ===============================================
# DEPLOYMENT - STOCK SERVICE
# ===============================================

resource "kubernetes_deployment" "stock_service" {
  metadata {
    name      = var.app_name
    namespace = kubernetes_namespace.stock.metadata[0].name

    labels = {
      app         = var.app_name
      service     = var.service
      environment = var.environment
    }
  }

  spec {
    replicas = var.replicas

    selector {
      match_labels = {
        app = var.app_name
      }
    }

    template {
      metadata {
        labels = {
          app         = var.app_name
          service     = var.service
          environment = var.environment
        }
      }

      spec {
        container {
          name  = var.app_name
          image = var.docker_image

          port {
            container_port = var.app_port
            protocol       = "TCP"
          }

          # Environment variables from ConfigMap
          env_from {
            config_map_ref {
              name = kubernetes_config_map.app_config.metadata[0].name
            }
          }

          # Environment variables from Secret
          env_from {
            secret_ref {
              name = kubernetes_secret.app_secret.metadata[0].name
            }
          }

          # Resource limits
          resources {
            requests = {
              cpu    = var.cpu_request
              memory = var.mem_request
            }
            limits = {
              cpu    = var.cpu_limit
              memory = var.mem_limit
            }
          }

          # Startup Probe - aguarda a aplicação iniciar
          startup_probe {
            http_get {
              path = "${var.health_check_path}/liveness"
              port = var.app_port
            }
            initial_delay_seconds = var.startup_initial_delay
            period_seconds        = 10
            timeout_seconds       = 5
            failure_threshold     = 30
            success_threshold     = 1
          }

          # Liveness Probe - verifica se aplicação está viva
          liveness_probe {
            http_get {
              path = "${var.health_check_path}/liveness"
              port = var.app_port
            }
            initial_delay_seconds = var.liveness_initial_delay
            period_seconds        = 30
            timeout_seconds       = 10
            failure_threshold     = 3
            success_threshold     = 1
          }

          # Readiness Probe - verifica se pode receber tráfego
          readiness_probe {
            http_get {
              path = "${var.health_check_path}/readiness"
              port = var.app_port
            }
            initial_delay_seconds = var.readiness_initial_delay
            period_seconds        = 10
            timeout_seconds       = 5
            failure_threshold     = 3
            success_threshold     = 1
          }

          # Security Context
          security_context {
            run_as_non_root             = true
            run_as_user                 = 1000
            allow_privilege_escalation  = false
            read_only_root_filesystem   = false
          }
        }

        # Pod Security Context
        security_context {
          fs_group = 1000
        }

        # Restart policy
        restart_policy = "Always"
      }
    }

    # Rolling update strategy
    strategy {
      type = "RollingUpdate"
      rolling_update {
        max_unavailable = 1
        max_surge       = 1
      }
    }
  }

  # Wait for rollout
  wait_for_rollout = true

  timeouts {
    create = "10m"
    update = "10m"
    delete = "5m"
  }
}
