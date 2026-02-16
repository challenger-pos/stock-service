# ===============================================
# SQS QUEUES FOR STOCK SERVICE
# ===============================================

# -----------------------------------------------
# 1. QUEUES CONSUMED BY STOCK SERVICE (Listeners)
# -----------------------------------------------

# Queue: work-order-stock-requested
# Receives stock reservation requests from work orders
resource "aws_sqs_queue" "stock_requested" {
  name                       = "${var.project}-work-order-stock-requested-${var.environment}"
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
      Name        = "${var.project}-work-order-stock-requested-${var.environment}"
      Consumer    = "stock-service"
      MessageType = "StockRequestedEvent"
    }
  )
}

resource "aws_sqs_queue" "stock_requested_dlq" {
  name                      = "${var.project}-work-order-stock-requested-dlq-${var.environment}"
  message_retention_seconds = 1209600 # 14 days

  tags = merge(
    var.tags,
    {
      Name = "${var.project}-work-order-stock-requested-dlq-${var.environment}"
      Type = "DeadLetterQueue"
    }
  )
}

# Queue: work-order-stock-approved
# Receives stock approval events to finalize reservation
resource "aws_sqs_queue" "stock_approved" {
  name                       = "${var.project}-work-order-stock-approved-${var.environment}"
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
      Name        = "${var.project}-work-order-stock-approved-${var.environment}"
      Consumer    = "stock-service"
      MessageType = "StockApprovedEvent"
    }
  )
}

resource "aws_sqs_queue" "stock_approved_dlq" {
  name                      = "${var.project}-work-order-stock-approved-dlq-${var.environment}"
  message_retention_seconds = 1209600

  tags = merge(
    var.tags,
    {
      Name = "${var.project}-work-order-stock-approved-dlq-${var.environment}"
      Type = "DeadLetterQueue"
    }
  )
}

# Queue: work-order-stock-cancel-requested
# Receives stock cancellation requests
resource "aws_sqs_queue" "stock_cancel_requested" {
  name                       = "${var.project}-work-order-stock-cancel-requested-${var.environment}"
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
      Name        = "${var.project}-work-order-stock-cancel-requested-${var.environment}"
      Consumer    = "stock-service"
      MessageType = "StockCancelRequestedEvent"
    }
  )
}

resource "aws_sqs_queue" "stock_cancel_requested_dlq" {
  name                      = "${var.project}-work-order-stock-cancel-requested-dlq-${var.environment}"
  message_retention_seconds = 1209600

  tags = merge(
    var.tags,
    {
      Name = "${var.project}-work-order-stock-cancel-requested-dlq-${var.environment}"
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
  name                       = "${var.project}-stock-reserved-queue-${var.environment}"
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
      Name        = "${var.project}-stock-reserved-queue-${var.environment}"
      Publisher   = "stock-service"
      MessageType = "StockReservedEvent"
    }
  )
}

resource "aws_sqs_queue" "stock_reserved_dlq" {
  name                      = "${var.project}-stock-reserved-queue-dlq-${var.environment}"
  message_retention_seconds = 1209600

  tags = merge(
    var.tags,
    {
      Name = "${var.project}-stock-reserved-queue-dlq-${var.environment}"
      Type = "DeadLetterQueue"
    }
  )
}

# Queue: stock-failed-queue
# Publishes when stock reservation fails
resource "aws_sqs_queue" "stock_failed" {
  name                       = "${var.project}-stock-failed-queue-${var.environment}"
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
      Name        = "${var.project}-stock-failed-queue-${var.environment}"
      Publisher   = "stock-service"
      MessageType = "StockFailedEvent"
    }
  )
}

resource "aws_sqs_queue" "stock_failed_dlq" {
  name                      = "${var.project}-stock-failed-queue-dlq-${var.environment}"
  message_retention_seconds = 1209600

  tags = merge(
    var.tags,
    {
      Name = "${var.project}-stock-failed-queue-dlq-${var.environment}"
      Type = "DeadLetterQueue"
    }
  )
}
