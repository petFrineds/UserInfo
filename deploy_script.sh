git pull
docker build -t userinfo-backend .
docker tag userinfo-backend:latest 811288377093.dkr.ecr.$AWS_REGION.amazonaws.com/userinfo-backend:latest
aws ecr get-login-password --region $AWS_REGION | docker login --username AWS --password-stdin 811288377093.dkr.ecr.us-west-2.amazonaws.com/
docker push 811288377093.dkr.ecr.us-west-2.amazonaws.com/userinfo-backend:latest
kubectl delete -f manifests/userInfo-deployment.yaml
kubectl apply -f manifests/userInfo-deployment.yaml
kubectl get pod
kubectl apply -f manifests/userInfo-service.yaml
kubectl get service
