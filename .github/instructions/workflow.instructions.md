---
applyTo: "**/*"
---

# 🔄 Instrucciones de Workflow - Reference App

## 🌟 Filosofía de Trabajo

Este documento define las reglas y procesos para mantener un flujo de trabajo ordenado, predecible y colaborativo en el repositorio Reference.

---

## 🌿 Estrategia de Branching

### Estructura de Ramas

```
main (producción)
└── develop (desarrollo principal)
    ├── feature/issue-1-setup-hilt
    ├── feature/issue-2-ui-implementation
    ├── feature/issue-3-business-logic
    └── hotfix/critical-bug-fix
```

### Tipos de Ramas

- **`main`**: Rama de producción, siempre estable
- **`develop`**: Rama de desarrollo principal, integración continua
- **`feature/issue-{number}-{description}`**: Desarrollo de nuevas funcionalidades
- **`hotfix/{description}`**: Correcciones críticas para producción
- **`bugfix/issue-{number}-{description}`**: Corrección de bugs no críticos

---

## 📋 Flujo de Trabajo por Issue

### 1. 🎯 Asignación y Preparación

```bash
# 1. Asignarse el issue en GitHub
# 2. Cambiar estado a "In Progress"
# 3. Crear rama desde develop
git checkout develop
git pull origin develop
git checkout -b feature/issue-{number}-{short-description}
```

**Ejemplo:**

```bash
git checkout -b feature/issue-1-setup-hilt
```

### 2. 🛠️ Desarrollo

**Reglas durante el desarrollo:**

- Commits frecuentes y atómicos
- Mensajes de commit descriptivos
- Seguir las instrucciones de arquitectura
- Mantener sincronización con develop

```bash
# Commits frecuentes
git add .
git commit -m "feat: add Hilt application class configuration"

# Sincronizar con develop periódicamente
git fetch origin
git rebase origin/develop
```

### 3. 📝 Convenciones de Commits

Usar **Conventional Commits**:

```
<type>[optional scope]: <description>

[optional body]

[optional footer(s)]
```

**Tipos permitidos:**

- `feat`: Nueva funcionalidad
- `fix`: Corrección de bug
- `docs`: Documentación
- `style`: Formato, punto y coma faltante, etc.
- `refactor`: Refactoring de código
- `test`: Añadir tests faltantes
- `chore`: Mantenimiento, dependencias, etc.

**Ejemplos:**

```bash
git commit -m "feat(ui): implement primary button atom component"
git commit -m "fix(navigation): resolve type-safe navigation serialization"
git commit -m "test(viewmodel): add unit tests for ShareViewModel"
git commit -m "docs: update architecture instructions"
```

---

## 🔄 Pull Request Workflow

### 1. 🚀 Creación de PR

**Antes de crear el PR:**

```bash
# Asegurar que está actualizado
git fetch origin
git rebase origin/develop

# Push de la rama
git push origin feature/issue-{number}-{description}
```

### 2. 📋 Template de PR

**Título del PR:**

```
[Issue #{number}] {Description}
```

**Ejemplo:**

```
[Issue #1] Setup inicial del proyecto con Dagger Hilt y dependencias base
```

**Template del cuerpo:**

```markdown
## 🎯 Issue Relacionado

Closes #1

## 📝 Descripción

Breve descripción de los cambios implementados.

## 🧪 Testing

- [ ] Unit tests añadidos/actualizados
- [ ] UI tests funcionando
- [ ] Testing manual completado

## 📋 Checklist

- [ ] Código sigue las instrucciones de arquitectura
- [ ] Tests pasan exitosamente
- [ ] No hay conflictos con develop
- [ ] Documentación actualizada (si aplica)
- [ ] Self-review completado

## 📱 Screenshots (si aplica)

[Añadir capturas de pantalla para cambios de UI]

## 📚 Notas Adicionales

[Cualquier información adicional relevante]
```

### 3. ✅ Validaciones Requeridas

**Automáticas (CI/CD):**

- [ ] Build exitoso
- [ ] Tests unitarios pasan
- [ ] Tests de UI pasan
- [ ] Linting sin errores
- [ ] Coverage mínimo 80%

**Manuales:**

- [ ] Code review aprobado por al menos 1 reviewer
- [ ] Arquitectura seguida correctamente
- [ ] No hay código duplicado
- [ ] Performance acceptable

---

## 👥 Code Review Guidelines

### Para el Autor

**Antes de solicitar review:**

- Self-review completo del código
- Verificar que todos los tests pasan
- Documentar decisiones complejas
- Limpiar commits (squash si es necesario)

### Para el Reviewer

**Criterios de evaluación:**

- **Arquitectura**: ¿Sigue las instrucciones definidas?
- **Clean Code**: ¿Es legible y mantenible?
- **Performance**: ¿Hay optimizaciones evidentes?
- **Testing**: ¿Está bien cubierto?
- **Security**: ¿Hay vulnerabilidades potenciales?

**Tipos de comentarios:**

- 🔴 **Blocking**: Debe corregirse antes del merge
- 🟡 **Suggestion**: Mejora recomendada pero no bloqueante
- 🟢 **Praise**: Reconocimiento de buen código

---

## 🔀 Merge Strategy

### Reglas de Merge

1. **Solo merge a develop** desde feature branches
2. **Squash and merge** para mantener historial limpio
3. **Rebase antes del merge** para evitar merge commits innecesarios
4. **Delete branch** después del merge exitoso

### Proceso de Merge

```bash
# 1. Reviewer aprueba el PR
# 2. Autor hace squash and merge desde GitHub UI
# 3. Eliminar rama feature automáticamente
# 4. Actualizar rama local
git checkout develop
git pull origin develop
git branch -d feature/issue-{number}-{description}
```

---

## 🚨 Hotfix Workflow

Para correcciones críticas en producción:

```bash
# 1. Crear hotfix desde main
git checkout main
git pull origin main
git checkout -b hotfix/critical-bug-description

# 2. Implementar fix
# 3. Crear PR hacia main Y develop
# 4. Merge inmediato tras approval
# 5. Tag de versión en main
```

---

## 📊 Métricas y Monitoreo

### KPIs del Workflow

- **Lead Time**: Tiempo desde issue a merge
- **Cycle Time**: Tiempo desde inicio desarrollo a merge
- **Review Time**: Tiempo de code review
- **Defect Rate**: Bugs encontrados post-merge

### Reportes Semanales

- Velocidad del equipo (issues completados)
- Quality metrics (test coverage, bugs)
- Code review effectiveness

---

## 🛡️ Protecciones de Rama

### Rama `main`

- ✅ Require PR reviews (mínimo 2)
- ✅ Require status checks (CI/CD)
- ✅ Require branches to be up to date
- ✅ Restrict pushes
- ✅ Require linear history

### Rama `develop`

- ✅ Require PR reviews (mínimo 1)
- ✅ Require status checks (CI/CD)
- ✅ Require branches to be up to date
- ✅ Restrict pushes

---

## 📋 Checklist de Issue Completion

Antes de cerrar un issue:

- [ ] Funcionalidad implementada completamente
- [ ] Tests añadidos y pasando
- [ ] Code review aprobado
- [ ] PR merged to develop
- [ ] Issue movido a "Done"
- [ ] Documentación actualizada (si aplica)
- [ ] Demo/screenshot añadido al issue

---

_"El orden en el flujo de trabajo es la base de un código inmortal"_ 💀⚡
