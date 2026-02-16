# ===============================================
# IDENTIFICAÇÃO
# ===============================================

variable "project" {
  description = "Nome do projeto"
  type        = string
}

variable "service" {
  description = "Nome do serviço"
  type        = string
}

variable "environment" {
  description = "Ambiente (dev, homolog, production)"
  type        = string
}

# ===============================================
# APLICAÇÃO
# ===============================================

variable "app_name" {
  description = "Nome da aplicação"
  type        = string
}

variable "app_port" {
  description = "Porta da aplicação"
  type        = number
  default     = 8080
}

variable "docker_image" {
  description = "Imagem Docker da aplicação"
  type        = string
}

# ===============================================
# RECURSOS KUBERNETES
# ===============================================

variable "replicas" {
  description = "Número de réplicas"
  type        = number
  default     = 2
}

variable "cpu_request" {
  description = "CPU request"
  type        = string
  default     = "250m"
}

variable "cpu_limit" {
  description = "CPU limit"
  type        = string
  default     = "1000m"
}

variable "mem_request" {
  description = "Memory request"
  type        = string
  default     = "512Mi"
}

variable "mem_limit" {
  description = "Memory limit"
  type        = string
  default     = "1Gi"
}

# ===============================================
# AUTOSCALING
# ===============================================

variable "hpa_min_replicas" {
  description = "Mínimo de réplicas do HPA"
  type        = number
  default     = 2
}

variable "hpa_max_replicas" {
  description = "Máximo de réplicas do HPA"
  type        = number
  default     = 10
}

variable "hpa_target_cpu_percentage" {
  description = "Target CPU para autoscaling"
  type        = number
  default     = 70
}

variable "hpa_target_memory_percentage" {
  description = "Target Memory para autoscaling"
  type        = number
  default     = 80
}

# ===============================================
# HEALTH CHECKS
# ===============================================

variable "health_check_path" {
  description = "Path do health check"
  type        = string
  default     = "/actuator/health"
}

variable "startup_initial_delay" {
  description = "Delay inicial da startup probe (segundos)"
  type        = number
  default     = 60
}

variable "liveness_initial_delay" {
  description = "Delay inicial da liveness probe (segundos)"
  type        = number
  default     = 30
}

variable "readiness_initial_delay" {
  description = "Delay inicial da readiness probe (segundos)"
  type        = number
  default     = 30
}

# ===============================================
# CONFIGURAÇÕES DA APLICAÇÃO
# ===============================================

variable "db_username" {
  description = "Username do banco de dados"
  type        = string
  sensitive   = true
}

variable "db_password" {
  description = "Senha do banco de dados"
  type        = string
  sensitive   = true
}

variable "spring_profiles_active" {
  description = "Spring profiles active"
  type        = string
  default     = "homologation"
}

variable "aws_region" {
  description = "AWS Region"
  type        = string
  default     = "us-east-2"
}

variable "aws_access_key" {
  description = "AWS Access Key para SQS"
  type        = string
  sensitive   = true
}

variable "aws_secret_key" {
  description = "AWS Secret Key para SQS"
  type        = string
  sensitive   = true
}

variable "jwt_secret" {
  description = "JWT Secret"
  type        = string
  sensitive   = true
  default     = "ZHVtbXktc2VjcmV0LWtleS1mb3Itand0LWhzMjU2LXN1cGVyLXNlY3VyZQ=="
}

# ===============================================
# TAGS
# ===============================================

variable "tags" {
  description = "Tags para os recursos"
  type        = map(string)
  default     = {}
}
