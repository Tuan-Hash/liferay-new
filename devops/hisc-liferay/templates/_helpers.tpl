{{/*
Expand the name of the chart.
*/}}
{{- define "hisc-liferay.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Create a default fully qualified app name.
We truncate at 63 chars because some Kubernetes name fields are limited to this (by the DNS naming spec).
If release name contains chart name it will be used as a full name.
*/}}
{{- define "hisc-liferay.fullname" -}}
{{- if .Values.fullnameOverride }}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- $name := default .Chart.Name .Values.nameOverride }}
{{- if contains $name .Release.Name }}
{{- .Release.Name | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" }}
{{- end }}
{{- end }}
{{- end }}

{{/*
Create chart name and version as used by the chart label.
*/}}
{{- define "hisc-liferay.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Common labels
*/}}
{{- define "hisc-liferay.labels" -}}
helm.sh/chart: {{ include "hisc-liferay.chart" . }}
{{ include "hisc-liferay.selectorLabels" . }}
{{- if .Chart.AppVersion }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
{{- end }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end }}

{{/*
Selector labels
*/}}
{{- define "hisc-liferay.selectorLabels" -}}
app.kubernetes.io/name: {{ include "hisc-liferay.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end }}

{{/*
Create the name of the service account to use
*/}}
{{- define "hisc-liferay.serviceAccountName" -}}
{{- if .Values.serviceAccount.create }}
{{- default (include "hisc-liferay.fullname" .) .Values.serviceAccount.name }}
{{- else }}
{{- default "default" .Values.serviceAccount.name }}
{{- end }}
{{- end }}

{{- define "hisc-liferay.init-containers-wait-for-es" -}}
  {{- if index .Values "elasticsearch" "enabled" }}
    {{- $es_port := default (int (index .Values "elasticsearch" "httpPort")) 9200 }}
- name: wait-for-es
  image: busybox:1.28
  command:
    - 'sh'
    - '-c'
    - >
      until nc -z -w 2 elasticsearch-master {{ $es_port }} && echo elasticsearch ok;
        do
          echo 'Waiting for ElasticSearch Connection...'
          sleep 2;
      done
  {{- with .Values.securityContext }}
  securityContext: {{- toYaml . | nindent 8 }}
  {{- end }}
  {{- end }}
{{- end -}}

{{/*
Create the custom env list for each deployment
*/}}
{{- define "hisc-liferay.customEnv" -}}
  {{- range $env, $value := .env }}
- name: {{ $env | quote }}
  value: {{ $value | quote }}
  {{- end }}
{{- end -}}