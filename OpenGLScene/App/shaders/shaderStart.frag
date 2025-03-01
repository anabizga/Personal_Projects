#version 410 core

in vec3 fNormal;
in vec4 fPosEye;
in vec2 fTexCoords;
in vec4 fragPosLightSpace;

out vec4 fColor;

//lighting
uniform vec3 lightDir;
uniform vec3 lightColor;
uniform vec3 pointLightPosition1;
uniform vec3 pointLightPosition2;
uniform vec3 pointLightPosition3;
uniform vec3 pointLightColor;
uniform vec3 pointLightAmbient;
uniform vec3 pointLightDiffuse;
uniform vec3 pointLightSpecular;
uniform float constantAttenuation;
uniform float linearAttenuation;
uniform float quadraticAttenuation;

//texture
uniform sampler2D diffuseTexture;
uniform sampler2D specularTexture;
uniform sampler2D shadowMap;
uniform bool useTexture;
uniform bool isNight;

vec3 ambient;
float ambientStrength = 0.2f;
vec3 diffuse;
vec3 specular;
float specularStrength = 0.5f;
float shininess = 32.0f;
float shadow = 1.0;

float computeShadow() {
    vec3 normalizedCoords = fragPosLightSpace.xyz / fragPosLightSpace.w;
    normalizedCoords = normalizedCoords * 0.5 + 0.5;

    if (normalizedCoords.z > 1.0f)
        return 0.0f;

    float closestDepth = texture(shadowMap, normalizedCoords.xy).r;
    float currentDepth = normalizedCoords.z;
    float bias = 0.005f;

    return (currentDepth - bias > closestDepth) ? 1.0f : 0.0f;
}

vec3 computeDayLight() {
    vec3 cameraPosEye = vec3(0.0f);
    vec3 normalEye = normalize(fNormal);
    vec3 lightDirN = normalize(lightDir);
    vec3 viewDirN = normalize(cameraPosEye - fPosEye.xyz);

    ambient = ambientStrength * lightColor;
    diffuse = max(dot(normalEye, lightDirN), 0.0f) * lightColor;

    vec3 reflection = reflect(-lightDirN, normalEye);
    float specCoeff = pow(max(dot(viewDirN, reflection), 0.0f), shininess);
    specular = specularStrength * specCoeff * lightColor;

    vec3 baseColor = vec3(0.9f, 0.35f, 0.0f);
    vec3 ambientColor = useTexture ? texture(diffuseTexture, fTexCoords).rgb : baseColor;
    vec3 diffuseColor = useTexture ? texture(diffuseTexture, fTexCoords).rgb : baseColor;
    vec3 specularColor = useTexture ? texture(specularTexture, fTexCoords).rgb : vec3(1.0f);

    ambient *= ambientColor;
    diffuse *= diffuseColor;
    specular *= specularColor;

    shadow = computeShadow();
    return ambient + (1.0f - shadow) * (diffuse + specular);
}

vec3 computePointLight(vec3 pointLightPosition) {
    vec3 normalEye = normalize(fNormal);
    vec3 fragPosEye = fPosEye.xyz;
    vec3 viewDirEye = normalize(-fPosEye.xyz);
    vec3 lightVec = pointLightPosition - fragPosEye;
    float dist = length(lightVec);
    vec3 lightVecN = normalize(lightVec);

    float attenuation = 1.0 / (constantAttenuation + linearAttenuation * dist + quadraticAttenuation * (dist * dist));

    vec3 ambientComp = pointLightAmbient * pointLightColor;

    float diffuseCoef = max(dot(normalEye, lightVecN), 0.0);
    vec3 diffuseComp = diffuseCoef * pointLightDiffuse * pointLightColor;

    vec3 reflection = reflect(-lightVecN, normalEye);
    float specularCoef = pow(max(dot(viewDirEye, reflection), 0.0), shininess);
    vec3 specularComp = specularCoef * pointLightSpecular * pointLightColor;

    ambientComp *= attenuation;
    diffuseComp *= attenuation;
    //specularComp *= attenuation;

    return ambientComp + diffuseComp;
}


float computeFog() {
    float fogDensity = 0.03f;
    float fragmentDistance = length(fPosEye);
    float fogFactor = exp(-pow(fragmentDistance * fogDensity, 2.0f));
    return clamp(fogFactor, 0.0f, 1.0f);
}

void main() {
    vec3 colorResult;

    if (isNight) {
	vec3 baseColor = texture(diffuseTexture, fTexCoords).rgb;
        vec3 specularColor = texture(specularTexture, fTexCoords).rgb;
        vec3 pointLight = computePointLight(pointLightPosition1);
	pointLight += computePointLight(pointLightPosition2);
	pointLight += computePointLight(pointLightPosition3);	
	colorResult = pointLight * baseColor;
    } else {
        colorResult = computeDayLight();
    }

    vec4 colorfinal = vec4(colorResult, 1.0f);
    float fogFactor = computeFog();
    vec4 fogColor = vec4(0.5f, 0.5f, 0.5f, 1.0f);
    fColor = mix(fogColor, colorfinal, fogFactor);
}


