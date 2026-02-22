# ===============================================
# SQS QUEUES FOR STOCK SERVICE
# ===============================================

# -----------------------------------------------
# 1. QUEUES CONSUMED BY STOCK SERVICE (Listeners)
# -----------------------------------------------

# Queue: work-order-stock-requested
# Receives stock reservation requests from work orders
resource "aws_sqs_queue" "stock_requested" {
  # name                       = "work-order-stock-requested-${var.environment}"
  name                       = "work-order-stock-requested"
  delay_seconds              = 0
  max_message_size           = 262144  # 256 KB
  message_retention_seconds  = 1209600 # 14 days
  receive_wait_time_seconds  = 10      # Long polling
  visibility_timeout_seconds = 300     # 5 minutes

  # Dead Letter Queue
  redrive_policy = jsonencode({
    deadLetterTargetArn = aws_sqs_queue.stock_requested_dlq.arn
    maxReceiveCount     = 3
  })

  tags = merge(
    var.tags,
    {
      #Name        = "work-order-stock-requested-${var.environment}"
      Name        = "work-order-stock-requested"
      Consumer    = "stock-service"
      MessageType = "StockRequestedEvent"
    }
  )
}

resource "aws_sqs_queue" "stock_requested_dlq" {
  #name                      = "work-order-stock-requested-dlq-${var.environment}"
  name                      = "work-order-stock-requested-dlq"
  message_retention_seconds = 1209600 # 14 days

  tags = merge(
    var.tags,
    {
      #Name = "work-order-stock-requested-dlq-${var.environment}"
      Name = "work-order-stock-requested-dlq"
      Type = "DeadLetterQueue"
    }
  )
}

# Queue: work-order-stock-approved
# Receives stock approval events to finalize reservation
resource "aws_sqs_queue" "stock_approved" {
  #name                       = "work-order-stock-approved-${var.environment}"
  name                       = "work-order-stock-approved"
  delay_seconds              = 0
  max_message_size           = 262144
  message_retention_seconds  = 1209600
  receive_wait_time_seconds  = 10
  visibility_timeout_seconds = 300

  redrive_policy = jsonencode({
    deadLetterTargetArn = aws_sqs_queue.stock_approved_dlq.arn
    maxReceiveCount     = 3
  })

  tags = merge(
    var.tags,
    {
      #Name        = "work-order-stock-approved-${var.environment}"
      Name        = "work-order-stock-approved"
      Consumer    = "stock-service"
      MessageType = "StockApprovedEvent"
    }
  )
}

resource "aws_sqs_queue" "stock_approved_dlq" {
  #name                      = "work-order-stock-approved-dlq-${var.environment}"
  name                      = "work-order-stock-approved-dlq"
  message_retention_seconds = 1209600

  tags = merge(
    var.tags,
    {
      #Name = "work-order-stock-approved-dlq-${var.environment}"
      Name = "work-order-stock-approved-dlq"
      Type = "DeadLetterQueue"
    }
  )
}

# Queue: work-order-stock-cancel-requested
# Receives stock cancellation requests
resource "aws_sqs_queue" "stock_cancel_requested" {
  #name                       = "work-order-stock-cancel-requested-${var.environment}"
  name                       = "work-order-stock-cancel-requested"
  delay_seconds              = 0
  max_message_size           = 262144
  message_retention_seconds  = 1209600
  receive_wait_time_seconds  = 10
  visibility_timeout_seconds = 300

  redrive_policy = jsonencode({
    deadLetterTargetArn = aws_sqs_queue.stock_cancel_requested_dlq.arn
    maxReceiveCount     = 3
  })

  tags = merge(
    var.tags,
    {
      #Name        = "work-order-stock-cancel-requested-${var.environment}"
      Name        = "work-order-stock-cancel-requested"
      Consumer    = "stock-service"
      MessageType = "StockCancelRequestedEvent"
    }
  )
}

resource "aws_sqs_queue" "stock_cancel_requested_dlq" {
  #name                      = "work-order-stock-cancel-requested-dlq-${var.environment}"
  name                      = "work-order-stock-cancel-requested-dlq"
  message_retention_seconds = 1209600

  tags = merge(
    var.tags,
    {
      #Name = "work-order-stock-cancel-requested-dlq-${var.environment}"
      Name = "work-order-stock-cancel-requested-dlq"
      Type = "DeadLetterQueue"
    }
  )
}

# -----------------------------------------------
# 2. QUEUES PUBLISHED BY STOCK SERVICE (Publisher)
# -----------------------------------------------

# Queue: stock-reserved-queue
# Publishes when stock reservation is successful
resource "aws_sqs_queue" "stock_reserved" {
  #name                       = "stock-reserved-queue-${var.environment}"
  name                       = "stock-reserved-queue"
  delay_seconds              = 0
  max_message_size           = 262144
  message_retention_seconds  = 1209600
  receive_wait_time_seconds  = 10
  visibility_timeout_seconds = 300

  redrive_policy = jsonencode({
    deadLetterTargetArn = aws_sqs_queue.stock_reserved_dlq.arn
    maxReceiveCount     = 3
  })

  tags = merge(
    var.tags,
    {
      #Name        = "stock-reserved-queue-${var.environment}"
      Name        = "stock-reserved-queue"
      Publisher   = "stock-service"
      MessageType = "StockReservedEvent"
    }
  )
}

resource "aws_sqs_queue" "stock_reserved_dlq" {
  #name                      = "stock-reserved-queue-dlq-${var.environment}"
  name                      = "stock-reserved-queue-dlq"
  message_retention_seconds = 1209600

  tags = merge(
    var.tags,
    {
      #Name = "stock-reserved-queue-dlq-${var.environment}"
      Name = "stock-reserved-queue-dlq"
      Type = "DeadLetterQueue"
    }
  )
}

# Queue: stock-failed-queue
# Publishes when stock reservation fails
resource "aws_sqs_queue" "stock_failed" {
  #name                       = "stock-failed-queue-${var.environment}"
  name                       = "stock-failed-queue"
  delay_seconds              = 0
  max_message_size           = 262144
  message_retention_seconds  = 1209600
  receive_wait_time_seconds  = 10
  visibility_timeout_seconds = 300

  redrive_policy = jsonencode({
    deadLetterTargetArn = aws_sqs_queue.stock_failed_dlq.arn
    maxReceiveCount     = 3
  })

  tags = merge(
    var.tags,
    {
      #Name        = "stock-failed-queue-${var.environment}"
      Name        = "stock-failed-queue"
      Publisher   = "stock-service"
      MessageType = "StockFailedEvent"
    }
  )
}

resource "aws_sqs_queue" "stock_failed_dlq" {
  #name                      = "stock-failed-queue-dlq-${var.environment}"
  name                      = "stock-failed-queue-dlq"
  message_retention_seconds = 1209600

  tags = merge(
    var.tags,
    {
      #Name = "stock-failed-queue-dlq-${var.environment}"
      Name = "stock-failed-queue-dlq"
      Type = "DeadLetterQueue"
    }
  )
}
