# Build Liferay image:
1. Create docker file
```
blade gw createDockerfile
```
2. Change the LIFERAY_WORKSPACE_ENVIRONMENT property at /build/docker/Dockerfile to the expect enviroment.
3. Add a RUN command before the COPY command to remove the trial license
```
RUN rm /opt/liferay/deploy/*license*.xml
```
4. Remove all files of /build/docker/deploy
5. Build the hisc workspace by command:  
All module will be build and move to /build/docker/deploy
```
# cd to the workspace folder
blade gw deploy
```
6. Copy the license file to /build/docker/deploy
7. Build Docker image
```
docker build -t  acrhioaas.azurecr.io/hisc-liferay:7.4.13-u50-MVP5-http-v3 .
```
8. Push it to Azure container registry
```
docker push acrhioaas.azurecr.io/hisc-liferay:7.4.13-u50-MVP5-http-v3
```
9. Update Image tag at /devops/hisc-liferay/values.yaml
10. Override ENV configs: (/devops/hisc-liferay/values.yaml)  
```
env:
  LIFERAY_JDBC_PERIOD_DEFAULT_PERIOD_DRIVER_UPPERCASEC_LASS_UPPERCASEN_AME: org.postgresql.Driver
  LIFERAY_JDBC_PERIOD_DEFAULT_PERIOD_URL: jdbc:postgresql://domain:5432/lportal
  LIFERAY_JDBC_PERIOD_DEFAULT_PERIOD_PASSWORD: password
  LIFERAY_JDBC_PERIOD_DEFAULT_PERIOD_USERNAME: sqladminuser
```
11. Deploy Helm Chart
```
helm install liferay .
```

# Configuration HTTPS
1. Install CSI driver  
```
helm repo add csi-secrets-store-provider-azure https://azure.github.io/secrets-store-csi-driver-provider-azure/charts

helm install csi csi-secrets-store-provider-azure/csi-secrets-store-provider-azure --set secrets-store-csi-driver.syncSecret.enabled=true
```

2. Create this secret to store Service Principal credential. This to be used to connect CSI drive and Key-vault  
```
kubectl create secret generic secrets-store-creds --from-literal clientid="xxxx" --from-literal clientsecret="xxxx"
```

3. Create a self-sign cert for tls configuration  
```
openssl req -x509 -nodes -days 365 -newkey rsa:2048 \
    -out aks-ingress-tls.crt \
    -keyout aks-ingress-tls.key 
```

4. This is for combinding Cert and Key into a Pfx file which support by Key-vault
```
openssl pkcs12 -export -in aks-ingress-tls.crt -inkey aks-ingress-tls.key  -out $CERT_NAME.pfx
```

5. Import Cert to Azure KeyVault  
```
az keyvault certificate import --vault-name $AKV_NAME -n $CERT_NAME -f $CERT_NAME.pfx
```
6. Create SecretProviderClass
```
apiVersion: secrets-store.csi.x-k8s.io/v1
kind: SecretProviderClass
metadata:
  name: hioaas-keyvault-dev
  namespace: default
spec:
  provider: azure
  secretObjects:
  - secretName: liferay-tls
    type: kubernetes.io/tls
    data: 
    - objectName: aks-liferay-cert
      key: tls.key
    - objectName: aks-liferay-cert
      key: tls.crt
  parameters:
    usePodIdentity: "false"
    useVMManagedIdentity: "false"
    userAssignedIdentityID: ""
    keyvaultName: "kv-hioaas-dev"
    objects: |
      array:
        - |
          objectName: aks-liferay-cert
          objectType: secret
          objectVersion: ""
    tenantId: "xxxx"
```
7. Mount secret store:
```
          volumeMounts:
            - name: secrets-mount
              mountPath: "/mnt/secrets-store"
              readOnly: true
      volumes:
        - name: secrets-mount
          csi:
            driver: secrets-store.csi.k8s.io
            readOnly: true
            volumeAttributes:
              secretProviderClass: "hioaas-keyvault-dev"
            nodePublishSecretRef:                       
              name: secrets-store-creds
```
8. Update ingress
```
ingress:
  enabled: true
  className: ""
  annotations:
    kubernetes.io/ingress.class: azure/application-gateway
    appgw.ingress.kubernetes.io/ssl-redirect: "true"
  hosts:
    - host: liferay.local
      paths:
        - path: /
          pathType: Prefix
  tls: 
      - hosts:
        - liferay.local
        secretName: liferay-tls
```
9. Deploy Helm Chart
```
helm install liferay .
```

# Configuration Azure File Shares as Persistent Volume
1. Create Storage account and File Shares.
2. Get Storage account name and Storage account key.
3. Create a Kubernetes secret:
```
kubectl create secret generic liferay-azure-secret --from-literal=azurestorageaccountname=stliferayhioaasdev --from-literal=azurestorageaccountkey=xxxx
```
4. Create Persistent volume and Persistent volume claim (pvc_liferay.yaml):
```
---
apiVersion: v1
kind: PersistentVolume
metadata:
  name: pv-azurefile-liferay
spec:
  capacity:
    storage: 5Gi
  accessModes:
    - ReadWriteMany
  persistentVolumeReclaimPolicy: Retain
  storageClassName: azurefile-csi
  csi:
    driver: file.csi.azure.com
    readOnly: false
    volumeHandle: unique-volumeid  # make sure this volumeid is unique in the cluster
    volumeAttributes:
      resourceGroup: rg-hioaas-dev # Account storage's resource group
      shareName: aksliferayshare   # File Shares name
    nodeStageSecretRef:
      name: liferay-azure-secret   # kube secret name from step 3
      namespace: default
  mountOptions:
    - dir_mode=0777
    - file_mode=0777
    - uid=0
    - gid=0
    - mfsymlinks
    - cache=strict
    - nosharesock
    - nobrl

---
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: pvc-azurefile-liferay
spec:
  accessModes:
    - ReadWriteMany
  storageClassName: azurefile-csi
  volumeName: pv-azurefile-liferay
  resources:
    requests:
      storage: 5Gi
```
5. Mount file share as a persistent volume (deployment.yaml)
```
          volumeMounts:
            - name: liferay-data-vol
              mountPath: /opt/liferay/data
      volumes:
        - name: liferay-data-vol
          persistentVolumeClaim:
            claimName: pvc-azurefile-liferay
```
6. Deploy Helm Chart
```
helm install liferay .
```