# Kubernetes manifests (stock-service)

Estrutura alinhada ao challengeOne: namespace, ConfigMap, Secret, Deployment (com Datadog), Service e HPA.

## Ordem de aplicação

1. **Namespace:** `kubectl apply -f stock-service-namespace.yaml`
2. **Datadog Agent** (uma vez por cluster, namespace default):  
   `kubectl create secret generic datadog-secret -n default --from-literal=api-key=SEU_DD_API_KEY`  
   `kubectl apply -f datadog-agent.yaml`
3. **Secret:** edite `stock-service-secret.yaml` (substitua REPLACE_*), depois:  
   `kubectl apply -f stock-service-secret.yaml`
4. **ConfigMap:** `kubectl apply -f stock-service-configmap.yaml`
5. **Deployment:** substitua `REPLACE_IMAGE` pela imagem do stock-service, depois:  
   `kubectl apply -f stock-service-deployment.yaml`
6. **Service:** `kubectl apply -f stock-service-service.yaml`
7. **HPA:** `kubectl apply -f stock-service-hpa.yaml`

Ou aplique a pasta (após editar Secret e imagem):  
`kubectl apply -f k8s/`

## Ajustes

- **ConfigMap:** altere nomes das filas SQS e região AWS se necessário.
- **Secret:** não versionar valores reais; use `kubectl create secret` ou um fluxo seguro.
- **Deployment:** `image` deve apontar para a imagem do stock-service (ex.: `seu-registry/stock-service:1.0.0`).
- **NodePort:** o Service usa `30081`; altere se houver conflito.
