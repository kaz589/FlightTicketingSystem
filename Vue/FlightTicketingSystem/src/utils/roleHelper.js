export function hasRole(payload, targetRole) {
  if (
    !payload.roles ||
    !Array.isArray(payload.roles) ||
    payload.roles.length === 0
  ) {
    return false;
  }

  const rolesString = payload.roles[0];
  const cleanRoles = rolesString
    .replace(/\[|\]/g, "")
    .split(",")
    .map((role) => role.trim());

  return cleanRoles.includes(targetRole);
}
